package com.gaojun.appmarket.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.http.protocol.HomeProtocol;
import com.gaojun.appmarket.ui.activities.HomeDetailActivity;
import com.gaojun.appmarket.ui.adapter.MyBaseAdapter;
import com.gaojun.appmarket.ui.holder.BaseHolder;
import com.gaojun.appmarket.ui.holder.HomeHeadHolder;
import com.gaojun.appmarket.ui.holder.HomeHolder;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.ui.view.MyListView;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 首页
 * Created by Administrator on 2016/6/27.
 */
public class HomeFragment extends BaseFragment {
    private ArrayList<AppInfo> data;
    private ArrayList<String> picurlList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        HomeHeadHolder headHolder = new HomeHeadHolder();
        view.addHeaderView(headHolder.initView());
        view.setAdapter(new HomeAdapter(data));
        if (picurlList != null) {
            headHolder.setData(picurlList);
        }
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UIUtils.getContext(),
                        HomeDetailActivity.class);
                intent.putExtra("packageName",data.get(position - 1).packageName);//去掉头布局
                startActivity(intent);
            }
        });
        return view;
    }

    //已经运行在子线程
    @Override
    public LoadingPage.ResultState onLoad() {
        //网络请求
//        data = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            data.add("item"+1);
//        }
        HomeProtocol protocol = new HomeProtocol();
        data = protocol.getData(0);
        picurlList = protocol.getPicUrlList();
        return check(data);
    }

    class HomeAdapter extends MyBaseAdapter<AppInfo> {
        public HomeAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int poistion) {
            return new HomeHolder();
        }

        //此方法在子线程
        @Override
        public ArrayList<AppInfo> onLoadMore() {
//            ArrayList<String> moreData = new ArrayList<>();
//            for (int i = 0; i < 20; i++) {
//                moreData.add("moreItem"+ i);
//            }
//            SystemClock.sleep(2000);
            HomeProtocol protocol = new HomeProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());

            return moreData;
        }

        //        public HomeAdapter(List<String> data) {
//            super(data);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;
//            if (convertView == null){
//                convertView = UIUtils.inflate(R.layout.list_item_home);
//                viewHolder = new ViewHolder();
//                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.textView);
//                convertView.setTag(viewHolder);
//            }else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//            viewHolder.tvContent.setText(data.get(position));
//            return convertView;
//        }
    }

    static class ViewHolder {
        public TextView tvContent;
    }
}
