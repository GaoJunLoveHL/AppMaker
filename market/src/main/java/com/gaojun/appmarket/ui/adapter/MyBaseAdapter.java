package com.gaojun.appmarket.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.gaojun.appmarket.ui.holder.BaseHolder;
import com.gaojun.appmarket.ui.holder.MoreHolder;
import com.gaojun.appmarket.utils.UIUtils;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/28.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_MORE = 0;

    private ArrayList<T> data;

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return TYPE_MORE;
        } else {
            return getInnerType(position);
        }
    }

    //子类可以重写此方法
    public int getInnerType(int position) {
        return TYPE_NORMAL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == TYPE_MORE) {
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder(position);
            }
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        if (getItemViewType(position) != TYPE_MORE) {
            holder.setData(getItem(position));
        } else {
            //加载更多布局
            //一旦加载更多布局展示出来，就开始加载更多
            MoreHolder moreHolder = (MoreHolder) holder;

            if (moreHolder.getData() == MoreHolder.STATE_LOAD_MORE){
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    public boolean hasMore() {
        return true;//默认都是有更多数据的
    }

    /**
     * 返回当前页面holder对象
     *
     * @return
     */
    public abstract BaseHolder<T> getHolder(int position);

    private boolean isLoadMore = false;//标记是否正在加载更多

    //加载更多数据
    public void loadMore(final MoreHolder holder) {
        if (!isLoadMore) {
            isLoadMore = true;
            new Thread() {
                @Override
                public void run() {

                    final ArrayList<T> moreData = onLoadMore();

                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {
                                //每一页20条数据,如果返回的数据小于20条，就认为到了最后一页了
                                if (moreData.size() < 20) {
                                    holder.setData(MoreHolder.STATE_LOAD_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(), "没有更多数据了",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    data.addAll(moreData);
                                    //还有更多
                                    holder.setData(MoreHolder.STATE_LOAD_MORE);
                                }

                                //刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                holder.setData(MoreHolder.STATE_LOAD_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            }.start();
        }

    }

    public abstract ArrayList<T> onLoadMore();

    public int getListSize(){
        return data.size();
    }
}
