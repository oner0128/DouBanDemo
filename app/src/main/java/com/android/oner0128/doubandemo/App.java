package com.android.oner0128.doubandemo;

import android.app.Application;
import android.util.Log;

import com.android.oner0128.doubandemo.util.DensityUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by rrr on 2017/4/22.
 */

public class App extends Application {
    private static App app;
    public static Application getContext(){return app;}
    public static final boolean ENCRYPTED = true;
//    private DaoSession daoSession;
public static int[] deviceWidthHeight;
    @Override
    public void onCreate() {
        super.onCreate();
        app =this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        deviceWidthHeight = DensityUtil.getDeviceInfo(app);
//        Log.v("devicewidthHeight:", deviceWidthHeight[0] + "/" + deviceWidthHeight[1]);
        deviceWidthHeight[0] /= 2;
        deviceWidthHeight[1] = (deviceWidthHeight[1] - DensityUtil.dip2px(app, 58 * 2.0f)) / 2;
        Logger.d(deviceWidthHeight);
//        Log.v("devicewidthHeight:", deviceWidthHeight[0] + "/" + deviceWidthHeight[1]);
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
