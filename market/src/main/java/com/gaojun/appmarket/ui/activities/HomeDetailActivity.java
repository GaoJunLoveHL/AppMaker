package com.gaojun.appmarket.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.domain.AppInfo;
import com.gaojun.appmarket.http.protocol.HomeDetailProtocol;
import com.gaojun.appmarket.ui.adapter.MyBaseAdapter;
import com.gaojun.appmarket.ui.holder.BaseHolder;
import com.gaojun.appmarket.ui.holder.DetailAppInfoHolder;
import com.gaojun.appmarket.ui.holder.DetailDescHoler;
import com.gaojun.appmarket.ui.holder.DetailDownloadHolder;
import com.gaojun.appmarket.ui.holder.DetailPicHolder;
import com.gaojun.appmarket.ui.holder.DetailSafeHolder;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;

public class HomeDetailActivity extends AppCompatActivity {

    private LoadingPage mLoadingPage;
    private AppInfo data;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingPage = new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };
        mLoadingPage.loadData();
        setContentView(mLoadingPage);

    }

    public View onCreateSuccessView() {
        View view = View.inflate(this,R.layout.page_home_detail,null);
        FrameLayout flDetailAppInfo = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        flDetailAppInfo.addView(appInfoHolder.getRootView());
        appInfoHolder.setData(data);

        FrameLayout flDetailSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DetailSafeHolder safeHolder = new DetailSafeHolder();
        flDetailSafe.addView(safeHolder.getRootView());
        safeHolder.setData(data);

        HorizontalScrollView hsv_pic = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pic);
        DetailPicHolder picHolder = new DetailPicHolder();
        hsv_pic.addView(picHolder.getRootView());
        picHolder.setData(data);

        FrameLayout flDetailDesc = (FrameLayout) view.findViewById(R.id.fl_detail_desc);
        DetailDescHoler descHoler = new DetailDescHoler();
        flDetailDesc.addView(descHoler.getRootView());
        descHoler.setData(data);

        FrameLayout flDetailDown = (FrameLayout) view.findViewById(R.id.fl_detail_download);
        DetailDownloadHolder downloadHolder = new DetailDownloadHolder();
        downloadHolder.setData(data);
        flDetailDown.addView(downloadHolder.getRootView());

        toolbar = (Toolbar)view.findViewById(R.id.tb_detail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_white);
        toolbar.setTitle("应用详情");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        return view;
    }

    public LoadingPage.ResultState onLoad() {
        Intent intent = getIntent();
        HomeDetailProtocol protocol = new HomeDetailProtocol(intent.getStringExtra("packageName"));
        data = protocol.getData(0);
        if (data != null) {
            return LoadingPage.ResultState.STATE_SUCCESS;
        } else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
