package com.gaojun.appmarket.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.CategroyInfo;
import com.gaojun.appmarket.http.HttpHelper;
import com.gaojun.appmarket.utils.BitmapHelper;
import com.gaojun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/6/29.
 */
public class CategoryHolder extends BaseHolder<CategroyInfo> implements View.OnClickListener{

    private TextView name1, name2, name3;
    private ImageView url1, url2, url3;
    private LinearLayout ll1, ll2, ll3;
    private BitmapUtils bitMapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_category);
        name1 = (TextView) view.findViewById(R.id.tv_name1);
        name2 = (TextView) view.findViewById(R.id.tv_name2);
        name3 = (TextView) view.findViewById(R.id.tv_name3);
        url1 = (ImageView) view.findViewById(R.id.iv_icon1);
        url2 = (ImageView) view.findViewById(R.id.iv_icon2);
        url3 = (ImageView) view.findViewById(R.id.iv_icon3);
        ll1 = (LinearLayout) view.findViewById(R.id.ll_grid1);
        ll2 = (LinearLayout) view.findViewById(R.id.ll_grid2);
        ll3 = (LinearLayout) view.findViewById(R.id.ll_grid3);
        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll3.setOnClickListener(this);
        bitMapUtils = BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void refershView(CategroyInfo data) {
        name1.setText(data.name1);
        name2.setText(data.name2);
        name3.setText(data.name3);
        bitMapUtils.display(url1, HttpHelper.URL + "image?name=" + data.url1);
        bitMapUtils.display(url2, HttpHelper.URL + "image?name=" + data.url2);
        bitMapUtils.display(url3, HttpHelper.URL + "image?name=" + data.url3);
    }

    @Override
    public void onClick(View v) {
        CategroyInfo info = getData();
        switch (v.getId()){
            case R.id.ll_grid1:
                Toast.makeText(UIUtils.getContext(),info.name1,Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid2:
                Toast.makeText(UIUtils.getContext(),info.name2,Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_grid3:
                Toast.makeText(UIUtils.getContext(),info.name3,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
