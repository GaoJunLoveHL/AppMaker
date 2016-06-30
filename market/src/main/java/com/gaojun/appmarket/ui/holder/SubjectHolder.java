package com.gaojun.appmarket.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.SubjectInfo;
import com.gaojun.appmarket.http.HttpHelper;
import com.gaojun.appmarket.utils.BitmapHelper;
import com.gaojun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 专题holder
 * Created by Administrator on 2016/6/29.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo>{
    private ImageView iv_pic;
    private TextView tv_desc;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);

        mBitmapUtils =  BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void refershView(SubjectInfo data) {
        tv_desc.setText(data.des);
        mBitmapUtils.display(iv_pic, HttpHelper.URL + "image?name=" + data.url);
    }
}
