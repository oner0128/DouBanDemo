package com.android.oner0128.doubandemo;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

/**
 * Created by rrr on 2017/4/22.
 */

public class App extends Application {
    private static App app;
    public static Application getContext(){return app;}
    public static final boolean ENCRYPTED = true;
//    private DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        app =this;
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
