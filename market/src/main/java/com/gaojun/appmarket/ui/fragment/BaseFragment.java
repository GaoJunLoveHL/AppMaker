package com.gaojun.appmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/27.
 */
public abstract class BaseFragment extends Fragment {
    private LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }

        };
        return mLoadingPage;
    }

    public abstract View onCreateSuccessView();

    public abstract LoadingPage.ResultState onLoad();
    //开始加载数据
    public void loadData() {
        if (mLoadingPage != null) {
            mLoadingPage.loadData();
        }
    }
    //对网络数据的合法性进行校验
    public LoadingPage.ResultState check(Object obj){
        if (obj != null){
            if (obj instanceof ArrayList){
                ArrayList list = (ArrayList) obj;
                if (list.isEmpty()){
                    return LoadingPage.ResultState.STATE_EMPTY;
                }else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
