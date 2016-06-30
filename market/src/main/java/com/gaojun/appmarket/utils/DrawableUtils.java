package com.gaojun.appmarket.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by Administrator on 2016/6/29.
 */
public class DrawableUtils {

    public static GradientDrawable getGradientDrawable(int color, int radius) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radius);
        shape.setColor(color);
        return shape;
    }

    public static StateListDrawable getSelector(Drawable normal, Drawable press) {
        StateListDrawable select = new StateListDrawable();
        select.addState(new int[]{android.R.attr.state_pressed}, press);
        select.addState(new int[]{}, normal);
        return select;
    }
    public static StateListDrawable getSelector(int normal, int press,int radius) {
        GradientDrawable bgNomal = getGradientDrawable(normal,radius);
        GradientDrawable bgPress = getGradientDrawable(press,radius);
        StateListDrawable select = getSelector(bgNomal,bgPress);
        return select;
    }
}
