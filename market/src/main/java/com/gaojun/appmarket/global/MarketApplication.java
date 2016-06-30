package com.gaojun.appmarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * 自定义application
 * Created by Administrator on 2016/6/27.
 */
public class MarketApplication extends Application{
    private static Context context;
    private static Handler handler;
    private static int mainThread;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = android.os.Process.myTid();//当前线程id，主线程id
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThread() {
        return mainThread;
    }
}
