package com.gaojun.appmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.http.protocol.AppProtocol;
import com.gaojun.appmarket.ui.adapter.MyBaseAdapter;
import com.gaojun.appmarket.ui.holder.AppHolder;
import com.gaojun.appmarket.ui.holder.BaseHolder;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.ui.view.MyListView;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 应用
 * Created by Administrator on 2016/6/27.
 */
public class AppFragment extends BaseFragment{
    private ArrayList<AppInfo> data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new AppAdapter(data));

        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol protocol = new AppProtocol();
        data = protocol.getData(0);

        return check(data);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int poistion) {
            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());

            return moreData;
        }
    }
}
