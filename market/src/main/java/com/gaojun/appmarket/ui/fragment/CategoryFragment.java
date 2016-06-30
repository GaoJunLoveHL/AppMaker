package com.gaojun.appmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaojun.appmarket.domain.CategroyInfo;
import com.gaojun.appmarket.http.protocol.CategoryProtocol;
import com.gaojun.appmarket.ui.adapter.MyBaseAdapter;
import com.gaojun.appmarket.ui.holder.BaseHolder;
import com.gaojun.appmarket.ui.holder.CategoryHolder;
import com.gaojun.appmarket.ui.holder.TitleHolder;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.ui.view.MyListView;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 专题
 * Created by Administrator on 2016/6/27.
 */
public class CategoryFragment extends BaseFragment {
    private ArrayList<CategroyInfo> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new CategroyAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class CategroyAdapter extends MyBaseAdapter<CategroyInfo> {


        public CategroyAdapter(ArrayList<CategroyInfo> data) {
            super(data);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        @Override
        public int getInnerType(int position) {
            CategroyInfo info = data.get(position);
            if (info.isTitle) {
                return super.getInnerType(position) + 1;
            } else {
                return super.getInnerType(position);
            }

        }

        @Override
        public boolean hasMore() {
            return false;//没有更多数据
        }

        @Override
        public BaseHolder<CategroyInfo> getHolder(int position) {
            CategroyInfo info = data.get(position);
            if (info.isTitle){
                return new TitleHolder();
            }else {
                return new CategoryHolder();
            }
        }

        @Override
        public ArrayList<CategroyInfo> onLoadMore() {
            return null;
        }
    }
}
