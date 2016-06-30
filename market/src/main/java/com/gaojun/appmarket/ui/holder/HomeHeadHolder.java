package com.gaojun.appmarket.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.http.HttpHelper;
import com.gaojun.appmarket.utils.BitmapHelper;
import com.gaojun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 首页轮播条
 * Created by Administrator on 2016/6/29.
 */
public class HomeHeadHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager pager;
    private ArrayList<String> data = new ArrayList<>();
    private LinearLayout llContainer;

    private int mPreviousPos;//上个图位置

    @Override
    public View initView() {
        //1.创建根部局
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
        rlRoot.setLayoutParams(params);
        //ViewPager
        pager = new ViewPager(UIUtils.getContext());

        RelativeLayout.LayoutParams vParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlRoot.addView(pager, vParams);

        llContainer = new LinearLayout(UIUtils.getContext());
        llContainer.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        int padding = UIUtils.dip2px(10);
        llContainer.setPadding(padding, padding, padding, padding);

        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        rlRoot.addView(llContainer, llParams);
        return rlRoot;
    }

    @Override
    public void refershView(final ArrayList<String> data) {
        this.data = data;
        pager.setAdapter(new HomeHeaderAdapter());
        pager.setCurrentItem(data.size() * 100000);

        //初始化指示器
        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            if (i == 0) {
                point.setImageResource(R.drawable.indicator_selected);
            } else {
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dip2px(4);
            }
            point.setLayoutParams(params);
            llContainer.addView(point);
        }
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % data.size();
                ImageView point = (ImageView) llContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                ImageView prePoint = (ImageView) llContainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);

                mPreviousPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        HomeHeaderTask task = new HomeHeaderTask();
        task.start();
    }

    class HomeHeaderTask implements Runnable {

        public void start() {
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this, 3000);
        }

        @Override
        public void run() {
            int currentItem = pager.getCurrentItem();
            currentItem++;
            pager.setCurrentItem(currentItem);
            UIUtils.getHandler().postDelayed(this, 3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter {
        private BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter() {
            mBitmapUtils = BitmapHelper.getmBitmapUtils();
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % data.size();
            String url = data.get(position);

            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(view, HttpHelper.URL + "image?name=" + url);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
