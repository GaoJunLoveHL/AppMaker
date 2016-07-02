package com.gaojun.appmarket.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.manager.ThreadManager;
import com.gaojun.appmarket.utils.UIUtils;

/**
 * 未加载、加载中、数据为空、加载成功
 * Created by Administrator on 2016/6/28.
 */
public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_LOAD_UNDO = 1;//未加载
    private static final int STATE_LOAD_LOADING = 2;//加载中
    private static final int STATE_LOAD_ERROR = 3;//加载失败
    private static final int STATE_LOAD_EMPTY = 4;//数据为空
    private static final int STATE_LOAD_SUCCESS = 5;//

    private int mCurrentState = STATE_LOAD_UNDO;//当前状态
    private View mLoadingPage, mErrorPage, mEmptyPage,mSuccessPage;

    public LoadingPage(Context context) {
        super(context);
        initView();

    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);
        }
        //初始化加载失败布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            Button btn_retry = (Button) mErrorPage.findViewById(R.id.btn_retry);

            btn_retry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //重新加载网络
                    loadData();
                }
            });

            addView(mErrorPage);
        }
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }

        showRightPage();
    }

    /**
     * 根据当前状态选择显示哪个布局
     */
    private void showRightPage() {
        mLoadingPage.setVisibility((mCurrentState == STATE_LOAD_UNDO ||
                mCurrentState == STATE_LOAD_LOADING) ? View.VISIBLE : View.GONE);
        mErrorPage.setVisibility((mCurrentState == STATE_LOAD_ERROR) ? View.VISIBLE : View.GONE);
        mEmptyPage.setVisibility((mCurrentState == STATE_LOAD_EMPTY) ? View.VISIBLE : View.GONE);

        if (mSuccessPage == null&& mCurrentState == STATE_LOAD_SUCCESS){
            mSuccessPage = onCreateSuccessView();
            if (mSuccessPage != null){
                addView(mSuccessPage);
            }
        }
        if (mSuccessPage != null){
            mSuccessPage.setVisibility((mCurrentState == STATE_LOAD_SUCCESS) ? View.VISIBLE : View.GONE);
        }
    }
    //开始加载线程
    public void loadData(){
        if (mCurrentState != STATE_LOAD_LOADING){
            mCurrentState = STATE_LOAD_LOADING;
//            new Thread(){
//                @Override
//                public void run() {
//                    final ResultState resultState = onLoad();
//                    UIUtils.runOnUIThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (resultState != null){
//                                mCurrentState = resultState.getState();//网络请求后的状态
//                                //根据状态更新UI
//                                showRightPage();
//                            }
//                        }
//                    });
//
//                }
//            }.start();
            ThreadManager.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {

                            if (resultState != null){
                                mCurrentState = resultState.getState();//网络请求后的状态
                                //根据状态更新UI
                                showRightPage();
                            }
                        }
                    });
                }
            });
        }
    }

    //子类自己实现
    public abstract View onCreateSuccessView();

    //加载网络数据
    public abstract ResultState onLoad();

    public enum ResultState{
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);
        private int state;

        private ResultState(int state) {
            this.state = state;
        }
        public int getState() {
            return state;
        }
    }
}
