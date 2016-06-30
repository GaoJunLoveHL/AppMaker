package com.gaojun.appmarket.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.gaojun.appmarket.R;

/**
 * Created by Administrator on 2016/6/29.
 */
public class RationLayout extends FrameLayout {
    private float ratio;

    public RationLayout(Context context) {
        super(context);
        initView();
    }

    public RationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RationLayout);
        int n = array.length();
        for (int i = 0; i < n; i++) {
            switch (i){
                case R.styleable.RationLayout_ratio:
                    ratio = array.getFloat(R.styleable.RationLayout_ratio,-1);
                    break;
            }
        }
        array.recycle();
    }

    public RationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY &&
                ratio>0){
            int imageWidth = width - getPaddingLeft() - getPaddingRight();
            int imageHeight = (int) (imageWidth/ratio);
            height = imageHeight + getPaddingTop() + getPaddingBottom();

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView() {

    }
}
