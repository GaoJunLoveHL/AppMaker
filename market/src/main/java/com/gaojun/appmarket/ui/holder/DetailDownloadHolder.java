package com.gaojun.appmarket.ui.holder;

import android.view.View;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.utils.UIUtils;

/**
 * Created by Administrator on 2016/7/2.
 */
public class DetailDownloadHolder extends BaseHolder<AppInfo>{
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);


        return view;
    }

    @Override
    public void refershView(AppInfo data) {

    }
}
