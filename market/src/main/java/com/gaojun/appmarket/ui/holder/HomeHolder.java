package com.gaojun.appmarket.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.http.HttpHelper;
import com.gaojun.appmarket.utils.BitmapHelper;
import com.gaojun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/6/28.
 */
public class HomeHolder extends BaseHolder<AppInfo> {
    private TextView tv_name, tv_size, tv_desc;
    private ImageView iv_icon;
    private RatingBar rb_star;

    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_home);
//        textView = (TextView) view.findViewById(R.id.textView);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_desc = (TextView) view.findViewById(R.id.tv_description);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

//        mBitmapUtils = new BitmapUtils(UIUtils.getContext());
        mBitmapUtils = BitmapHelper.getmBitmapUtils();
        return view;
    }


    @Override
    public void refershView(AppInfo data) {
//        textView.setText(data.name);
        tv_name.setText(data.name);
        tv_size.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tv_desc.setText(data.des);
        rb_star.setRating(data.stars);

        mBitmapUtils.display(iv_icon, HttpHelper.URL + "image?name=" + data.iconUrl);
    }
}
