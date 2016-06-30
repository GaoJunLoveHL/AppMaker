package com.gaojun.appmarket.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.http.protocol.HotProtocol;
import com.gaojun.appmarket.ui.view.FlowLayout;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.utils.DrawableUtils;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 排行
 * Created by Administrator on 2016/6/27.
 */
public class HotFragment extends BaseFragment{
    private ArrayList<String> data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());

        int padding = UIUtils.dip2px(10);
        flowLayout.setPadding(padding,padding,padding,padding);
        flowLayout.setHorizontalSpacing(UIUtils.dip2px(6));
        flowLayout.setVerticalSpacing(UIUtils.dip2px(8));
        for (int i = 0; i < data.size(); i++) {
            final String keyWord = data.get(i);

            TextView view = new TextView(UIUtils.getContext());
            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            view.setPadding(padding,padding,padding,padding);
            view.setGravity(Gravity.CENTER);

            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = random.nextInt(230);
            int b = random.nextInt(230);

            int color = 0xffcecece;
//            GradientDrawable bgNomal = DrawableUtils.getGradientDrawable(
//                    Color.rgb(r,g,b),UIUtils.dip2px(6));
            view.setText(keyWord);
            StateListDrawable drawable =  DrawableUtils.getSelector(Color.rgb(r,g,b),color,UIUtils.dip2px(6));
            view.setBackgroundDrawable(drawable);
            flowLayout.addView(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),keyWord,Toast.LENGTH_SHORT).show();
                }
            });
        }

        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        data = protocol.getData(0);
        return check(data);
    }
}
