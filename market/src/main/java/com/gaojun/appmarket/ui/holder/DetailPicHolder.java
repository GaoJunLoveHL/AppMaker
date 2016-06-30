package com.gaojun.appmarket.ui.holder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.http.HttpHelper;
import com.gaojun.appmarket.utils.BitmapHelper;
import com.gaojun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/30.
 */
public class DetailPicHolder extends BaseHolder<AppInfo> {
    private ImageView[] iv_pics;
    private BitmapUtils mBitmap;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
        iv_pics = new ImageView[5];
        iv_pics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        iv_pics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        iv_pics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        iv_pics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        iv_pics[4] = (ImageView) view.findViewById(R.id.iv_pic5);
        mBitmap = BitmapHelper.getmBitmapUtils();
        return view;
    }

    @Override
    public void refershView(AppInfo data) {

        final ArrayList<String> urls = data.screen;
        for (int i = 0; i < 5; i++) {
            if (i<urls.size())
            mBitmap.display(iv_pics[i], HttpHelper.URL + "image?name=" + urls.get(i));
//            iv_pics[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.putExtra("list",urls);
//                }
//            });
            else {
                iv_pics[i].setVisibility(View.GONE);
            }
        }

    }
}
