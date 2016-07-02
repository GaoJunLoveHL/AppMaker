package com.gaojun.appmarket.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.domain.DownloadInfo;
import com.gaojun.appmarket.manager.DownloadManager;
import com.gaojun.appmarket.ui.view.ProgressHorizontal;
import com.gaojun.appmarket.utils.UIUtils;

/**
 * Created by Administrator on 2016/7/2.
 */
public class DetailDownloadHolder extends BaseHolder<AppInfo> implements
        DownloadManager.DownloadObServer,View.OnClickListener{
    private DownloadManager mDm;
    private float mProgress;
    private int mCurrentState;
    private  FrameLayout flProgress;
    private Button btnDownload;
    private ProgressHorizontal pbProgress;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);

        btnDownload = (Button) view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
        flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);
        flProgress.setOnClickListener(this);
        pbProgress = new ProgressHorizontal(UIUtils.getContext());

        pbProgress.setProgressBackgroundResource(R.drawable.progress_bg);
        pbProgress.setProgressResource(R.drawable.progress_normal);
        pbProgress.setProgressTextColor(Color.WHITE);
        pbProgress.setProgressTextSize(UIUtils.dip2px(18));

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.MATCH_PARENT);

        flProgress.addView(pbProgress);

        mDm = DownloadManager.getInstance();
        mDm.registerObserver(this);//注册观察者,监听状态和进度变化

        return view;
    }

    @Override
    public void refershView(AppInfo data) {
        //判断当前应用是否下载过
        DownloadInfo downloadInfo = mDm.getDownloadInfo(data);
        if (downloadInfo != null){
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        }else {
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }
        refreshUI(mCurrentState,mProgress);
    }

    private void refreshUI(int currentState, float progress) {
        mCurrentState = currentState;
        mProgress = progress;
        switch (currentState){
            case DownloadManager.STATE_UNDO:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;
            case DownloadManager.STATE_WAITTING:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中。。");
                break;
            case DownloadManager.STATE_DOWNLOADING:
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("");
                pbProgress.setProgress(mProgress);
                break;
            case DownloadManager.STATE_PAUSE:
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("暂停");
                pbProgress.setProgress(mProgress);
                break;
            case DownloadManager.STATE_ERROR:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载失败");
                break;
            case DownloadManager.STATE_SUCCESS:
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;
        }
    }

    private void refreshUIOnMainThread(final DownloadInfo info){
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                refreshUI(info.currentState,info.getProgress());
            }
        });
    }

    //状态更新
    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        AppInfo appInfo = getData();
        if (appInfo.id.equals(info.id)){
            refreshUIOnMainThread(info);
        }
    }

    //进度更新
    @Override
    public void onDownloadProgressChanged(DownloadInfo info) {
        AppInfo appInfo = getData();
        if (appInfo.id.equals(info.id)){
            refreshUIOnMainThread(info);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_download:

            case R.id.fl_progress:
                if (mCurrentState == DownloadManager.STATE_UNDO ||
                        mCurrentState == DownloadManager.STATE_ERROR||
                        mCurrentState == DownloadManager.STATE_PAUSE){
                    mDm.download(getData());
                }else if (mCurrentState == DownloadManager.STATE_DOWNLOADING ||
                        mCurrentState == DownloadManager.STATE_WAITTING){
                    mDm.pause(getData());
                }else if (mCurrentState == DownloadManager.STATE_SUCCESS){
                    mDm.install(getData());
                }
                break;
        }
    }
}
