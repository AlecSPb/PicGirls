package com.perasia.picgirls;


import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication mInstance = null;

    private static Context mContext;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        mContext = getApplicationContext();

        init();
    }

    private void init() {

    }
}
