package com.gaojun.appmarket.domain;

import android.os.Environment;

import com.gaojun.appmarket.manager.DownloadManager;

import java.io.File;

/**
 * 下载对象
 * Created by Administrator on 2016/7/2.
 */
public class DownloadInfo {
    public String id;
    public String name;
    public String downloadUrl;
    public long size;
    public String packageName;

    public long currentPos;//当前下载位置
    public int currentState;//当前的下载状态

    public String path;//下载到本地文件的路径

    public static final String APP_MARKET = "APP_MARKET";
    public static final String DOWNLOAD = "download";

    public float getProgress() {
        if (size == 0){
            return 0;
        }
        float progress = currentPos / (float) size;
        return progress;
    }

    //copy对象的方法
    public static DownloadInfo copy(AppInfo info){
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = info.id;
        downloadInfo.name = info.name;
        downloadInfo.downloadUrl = info.downloadUrl;
        downloadInfo.packageName = info.packageName;
        downloadInfo.size = info.size;
        downloadInfo.currentPos = 0;
        downloadInfo.currentState = DownloadManager.STATE_UNDO;

        downloadInfo.path = downloadInfo.getFilePath();
        return downloadInfo;
    }

    public String getFilePath() {
        StringBuffer sb = new StringBuffer();
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        sb.append(sdcard);
        sb.append(File.separator);
        sb.append(APP_MARKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);
        if (createDir(sb.toString())) {
            return sb.toString() + File.separator + name + ".apk";
        }
        return null;
    }

    private boolean createDir(String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return fileDir.mkdirs();
        }
        return true;
    }
}
