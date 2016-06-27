package com.gaojun.appmarket.ui.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.ui.view.PagerTab;

public class MainActivity extends BaseActivity {

    private PagerTab mPagerTab;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
    }
}
