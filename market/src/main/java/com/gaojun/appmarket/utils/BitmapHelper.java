package com.gaojun.appmarket.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Administrator on 2016/6/29.
 */
public class BitmapHelper {
    private static BitmapUtils mBitmapUtils = null;

    //单例 懒汉模式
    public static BitmapUtils getmBitmapUtils(){
        if (mBitmapUtils == null){
            synchronized (BitmapHelper.class){
                if (mBitmapUtils == null){
                    mBitmapUtils = new BitmapUtils(UIUtils.getContext());
                }
            }
        }
        return mBitmapUtils;
    }

}
