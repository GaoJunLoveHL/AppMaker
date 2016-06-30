package com.gaojun.appmarket.ui.holder;

import android.view.View;

/**
 * 封装holder
 * Created by Administrator on 2016/6/28.
 */
public abstract class BaseHolder<T> {
    private View rootView;
    private T data;

    public BaseHolder() {
        rootView = initView();
        //3.标记tag
        rootView.setTag(this);
    }

    public View getRootView() {
        return rootView;
    }

    /**
     * 1、加载布局文件
     * 2、初始化控件findViewById
     *
     * @return
     */
    public abstract View initView();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refershView(data);
    }

    /**
     * 4、根据数据来刷新界面
     *
     * @param data
     */
    public abstract void refershView(T data);

}
