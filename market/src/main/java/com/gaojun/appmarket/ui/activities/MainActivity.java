package com.gaojun.appmarket.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.ui.fragment.BaseFragment;
import com.gaojun.appmarket.ui.fragment.FragmentFactory;
import com.gaojun.appmarket.ui.view.PagerTab;
import com.gaojun.appmarket.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private PagerTab mPagerTab;
    private ViewPager mViewPager;
    private VPAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter = new VPAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mPagerTab.setViewPager(mViewPager);

        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                BaseFragment baseFragment = FragmentFactory.createFragment(position);
                baseFragment.loadData();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class VPAdapter extends FragmentPagerAdapter{
        private String[] array;
        public VPAdapter(FragmentManager fm) {
            super(fm);
            array = UIUtils.getStringArray(R.array.tab_names);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return array[position];
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return array.length;
        }
    }

}
