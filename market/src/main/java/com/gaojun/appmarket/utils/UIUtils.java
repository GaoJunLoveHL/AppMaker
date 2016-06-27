package com.gaojun.appmarket.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.gaojun.appmarket.global.MarketApplication;

/**
 * Created by Administrator on 2016/6/27.
 */
public class UIUtils {
    public static Context getContext() {
        return MarketApplication.getContext();
    }

    public static Handler getHandler() {
        return MarketApplication.getHandler();
    }

    public static int getThraedId() {
        return MarketApplication.getMainThread();
    }
/******************************加载资源文件********************************/
    /**
     * 获取String
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 获取String[]
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /**
     * 获取drawable
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 获取color
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    /**
     * 获取dimen像素值
     *
     * @param id
     * @return
     */
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }
/*********************************************************************/
    /**
     * dip转px
     *
     * @param dip
     * @return
     */
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    /**
     * px转dip
     *
     * @param px
     * @return
     */
    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }
    /***********************加载布局文件***************************/
    /**
     * 加载布局
     * @param id
     * @return
     */
    public static View inflate(int id){
        return View.inflate(getContext(),id,null);
    }
    /**
     * 判断是否是主线程
     * @param
     * @return
     */
    public static boolean isRunOnUIThread(){
        return (android.os.Process.myTid()==getThraedId()?true:false);
    }

    /**
     * 运行在主线程
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable){
        if (isRunOnUIThread()){
            runnable.run();
        }else {
            //如果是子线程，借助handler
            getHandler().post(runnable);
        }
    }


}
