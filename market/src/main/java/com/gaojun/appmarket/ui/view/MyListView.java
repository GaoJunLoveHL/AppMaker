package com.gaojun.appmarket.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/6/29.
 */
public class MyListView extends ListView{
    public MyListView(Context context) {
        super(context);
        initView();

    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setDivider(null);
        this.setCacheColorHint(Color.TRANSPARENT);
    }
}
