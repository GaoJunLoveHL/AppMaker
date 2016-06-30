package com.gaojun.appmarket.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.CategroyInfo;
import com.gaojun.appmarket.utils.UIUtils;

/**
 * Created by Administrator on 2016/6/29.
 */
public class TitleHolder extends BaseHolder<CategroyInfo>{
    private TextView tv_titile;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        tv_titile = (TextView) view.findViewById(R.id.tv_title);
        tv_titile.setTextColor(Color.BLACK);
        return view;
    }

    @Override
    public void refershView(CategroyInfo data) {
        tv_titile.setText(data.title);
    }
}
