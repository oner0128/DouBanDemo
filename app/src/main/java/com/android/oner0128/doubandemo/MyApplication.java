package com.android.oner0128.doubandemo;

import android.app.Application;

/**
 * Created by rrr on 2017/4/22.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;
    public static Application getContext(){return myApplication;}

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
    }
}
