package com.gaojun.appmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.utils.UIUtils;

/**
 * 游戏
 * Created by Administrator on 2016/6/27.
 */
public class GameFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getSimpleName());
        view.setTextColor(UIUtils.getColor(R.color.tab_text_color_normal));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
