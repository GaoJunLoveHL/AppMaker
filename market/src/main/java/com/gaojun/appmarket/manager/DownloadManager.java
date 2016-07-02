package com.gaojun.appmarket.manager;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.domain.DownloadInfo;
import com.gaojun.appmarket.http.HttpHelper;
import com.gaojun.appmarket.utils.IOUtils;
import com.gaojun.appmarket.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 下载管理器
 * <p/>
 * DownloadManager:被观察者，有责任通知被观察者状态变化
 * Created by Administrator on 2016/7/2.
 */
public class DownloadManager {
    public static final int STATE_UNDO = 1;
    public static final int STATE_WAITTING = 2;
    public static final int STATE_DOWNLOADING = 3;
    public static final int STATE_PAUSE = 4;
    public static final int STATE_ERROR = 5;
    public static final int STATE_SUCCESS = 6;
    private static DownloadManager mDM = new DownloadManager();

    private ArrayList<DownloadObServer> mObServers = new ArrayList<>();
    //线程安全的hashmap
    private ConcurrentHashMap<String, DownloadInfo> mDownloadInfoMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, DownloadTask> mDownloadTaskMap = new ConcurrentHashMap<>();

    private DownloadManager() {
    }

    public static DownloadManager getInstance() {
        return mDM;
    }

    //2、注册观察者
    public void registerObserver(DownloadObServer obServer) {
        if (obServer != null && !mObServers.contains(obServer)) {
            mObServers.add(obServer);
        }
    }

    //3、注销观察者
    public void unRegisterObserver(DownloadObServer obServer) {

        if (obServer != null && mObServers.contains(obServer)) {
            mObServers.remove(obServer);
        }
    }

    //5、通知下载状态发生变化
    public void notifyDownloadStateChanged(DownloadInfo info) {
        for (DownloadObServer obServer : mObServers) {
            obServer.onDownloadStateChanged(info);
        }
    }

    //6、通知进度发生变化
    public void notifyDownloadProgressChanged(DownloadInfo info) {
        for (DownloadObServer obServer : mObServers) {
            obServer.onDownloadProgressChanged(info);
        }
    }

    public void download(AppInfo appInfo) {
        //如果对象是第一次下载需要创建一个新的DownloadInfo对象,否则
        //断电续传
        DownloadInfo downloadInfo = mDownloadInfoMap.get(appInfo.id);
        if (downloadInfo == null) {
            downloadInfo = DownloadInfo.copy(appInfo);
        }
        downloadInfo.currentState = STATE_WAITTING;//切换为等待状态
        notifyDownloadStateChanged(downloadInfo);//通知所有观察者状态发生变化了
        mDownloadInfoMap.put(downloadInfo.id, downloadInfo);

        //初始化下载任务，并放入线程池
        DownloadTask task = new DownloadTask(downloadInfo);
        ThreadManager.getThreadPool().execute(task);

        mDownloadTaskMap.put(downloadInfo.id, task);
    }

    class DownloadTask implements Runnable {
        private DownloadInfo downloadInfo;

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void run() {
            Log.d("开始下载", "开始下载");
            downloadInfo.currentState = STATE_DOWNLOADING;
            notifyDownloadStateChanged(downloadInfo);
            File file = new File(downloadInfo.path);
            HttpHelper.HttpResult httpResult = null;
            if (!file.exists() || file.length() != downloadInfo.currentPos ||
                    file.length() == 0) {
                //从头开始下载
                //删除无效文件
                file.delete();
                downloadInfo.currentPos = 0;//当前下载位置为0
                httpResult = HttpHelper.download(HttpHelper.URL +
                        "download?name=" + downloadInfo.downloadUrl);
            } else {
                //断点续传
                //range表示请求服务器从文件的哪个位置开始返回数据
                httpResult = HttpHelper.download(HttpHelper.URL +
                        "download?name=" + downloadInfo.downloadUrl + "&range=" + file.length());
            }
            if (httpResult != null && httpResult.getInputStream() != null) {
                InputStream in = httpResult.getInputStream();
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file, true);//在原有文件基础上追加
                    int len = 0;
                    byte[] buffer = new byte[1024*4];
                    //只有状态是正在下载，才继续轮询.解决下载过程中途暂停的问题
                    while ((len = in.read(buffer)) != -1 &&
                            downloadInfo.currentState == STATE_DOWNLOADING) {
                        out.write(buffer, 0, len);
                        out.flush();

                        downloadInfo.currentPos += len;
                        notifyDownloadProgressChanged(downloadInfo);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(in);
                    IOUtils.close(out);
                }
                //文件已经下载结束
                if (file.length() == downloadInfo.size) {
                    //下载成功
                    downloadInfo.currentState = STATE_SUCCESS;
                    notifyDownloadStateChanged(downloadInfo);
                } else if (downloadInfo.currentState == STATE_PAUSE) {
                    //中途暂停
                    notifyDownloadStateChanged(downloadInfo);
                } else {
                    file.delete();
                    downloadInfo.currentState = STATE_ERROR;
                    downloadInfo.currentPos = 0;
                    notifyDownloadStateChanged(downloadInfo);
                }
            } else {
                //网络异常
                file.delete();
                downloadInfo.currentState = STATE_ERROR;
                downloadInfo.currentPos = 0;
                notifyDownloadStateChanged(downloadInfo);
            }
            mDownloadTaskMap.remove(downloadInfo.id);
        }
    }

    public void pause(AppInfo info) {
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.id);
        if (downloadInfo != null) {
            if (downloadInfo.currentState == STATE_DOWNLOADING ||
                    downloadInfo.currentState == STATE_WAITTING) {
                downloadInfo.currentState = STATE_PAUSE;
                notifyDownloadStateChanged(downloadInfo);

                DownloadTask task = mDownloadTaskMap.get(downloadInfo.id);
                if (task != null) {
                    //如果任务还没开始，正在等待，可以通过此方法移除
                    ThreadManager.getThreadPool().cancel(task);
                }
            }
        }
    }

    //开始安装
    public void install(AppInfo info) {
        DownloadInfo downloadInfo = mDownloadInfoMap.get(info.id);
        if (downloadInfo != null) {
            // 跳到系统的安装页面进行安装
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + downloadInfo.path),
                    "application/vnd.android.package-archive");
            UIUtils.getContext().startActivity(intent);
        }
    }

    /**
     * 1、声明观察者接口
     */
    public interface DownloadObServer {
        //下载状态发生变化
        public void onDownloadStateChanged(DownloadInfo info);

        //下载进度发生变化
        public void onDownloadProgressChanged(DownloadInfo info);
    }

    public DownloadInfo getDownloadInfo(AppInfo id){
        return mDownloadInfoMap.get(id);
    }
}
