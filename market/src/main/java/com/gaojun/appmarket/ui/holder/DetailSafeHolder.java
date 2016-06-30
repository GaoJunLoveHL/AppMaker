package com.gaojun.appmarket.ui.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class DetailSafeHolder extends BaseHolder<AppInfo> {
    private ImageView[] mSafeIcons;// 安全标识图片
    private ImageView[] mDesIcons;// 安全描述图片
    private TextView[] mSafeDes;// 安全描述文字
    private LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
    private BitmapUtils mBitmapUtils;

    private RelativeLayout rlDesRoot;
    private LinearLayout llDesRoot;
    private ImageView ivArrow;

    private int mDesHeight;
    private LinearLayout.LayoutParams mParams;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);

        mSafeIcons = new ImageView[4];
        mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
        mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
        mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
        mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

        mDesIcons = new ImageView[4];
        mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
        mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
        mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
        mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

        mSafeDes = new TextView[4];
        mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
        mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
        mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
        mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

        mSafeDesBar = new LinearLayout[4];
        mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
        mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
        mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
        mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);

        rlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
        rlDesRoot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        mBitmapUtils = BitmapHelper.getmBitmapUtils();

        llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);


        return view;
    }

    @Override
    public void refershView(AppInfo data) {
        ArrayList<AppInfo.SafeInfo> safe = data.safe;

        for (int i = 0; i < 4; i++) {
            if (i < safe.size()) {
                // 安全标识图片
                AppInfo.SafeInfo safeInfo = safe.get(i);
                mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL
                        + "image?name=" + safeInfo.safeUrl);
                // 安全描述文字
                mSafeDes[i].setText(safeInfo.safeDes);
                // 安全描述图片
                mBitmapUtils.display(mDesIcons[i], HttpHelper.URL
                        + "image?name=" + safeInfo.safeDesUrl);
            } else {
                // 剩下不应该显示的图片
                mSafeIcons[i].setVisibility(View.GONE);

                // 隐藏多余的描述条目
                mSafeDesBar[i].setVisibility(View.GONE);
            }
        }

        llDesRoot.measure(0,0);
        mDesHeight =  llDesRoot.getMeasuredHeight();

        mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
        mParams.height = 0;
        llDesRoot.setLayoutParams(mParams);
    }
    private boolean isOpen = false;
    private void toggle() {
        ValueAnimator animator;
        if (isOpen){
            animator = ValueAnimator.ofInt(mDesHeight,0);
            isOpen = false;
        }else {
            animator = ValueAnimator.ofInt(0,mDesHeight);
            isOpen = true;
        }

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height = (Integer) animation.getAnimatedValue();
                mParams.height = height;
                llDesRoot.setLayoutParams(mParams);
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpen){
                    ivArrow.setImageResource(R.drawable.arrow_down);
                }else {
                    ivArrow.setImageResource(R.drawable.arrow_up);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.setDuration(200);
        animator.start();
    }
}
