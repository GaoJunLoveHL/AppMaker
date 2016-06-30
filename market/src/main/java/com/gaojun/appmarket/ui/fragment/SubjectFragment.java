package com.gaojun.appmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gaojun.appmarket.domain.SubjectInfo;
import com.gaojun.appmarket.http.protocol.SubjectProtocol;
import com.gaojun.appmarket.ui.adapter.MyBaseAdapter;
import com.gaojun.appmarket.ui.holder.BaseHolder;
import com.gaojun.appmarket.ui.holder.SubjectHolder;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.ui.view.MyListView;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 分类
 * Created by Administrator on 2016/6/27.
 */
public class SubjectFragment extends BaseFragment{
    private ArrayList<SubjectInfo> data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new SubjectAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol protocol = new SubjectProtocol();
        data = protocol.getData(0);

        return check(data);
    }

    private class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {
        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder(int poistion) {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            ArrayList<SubjectInfo> moreData = protocol.getData(getListSize());

            return moreData;
        }
    }
}
