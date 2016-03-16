package com.perasia.picgirls;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.perasia.picgirls.utils.CommonUtils;

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

        createPicPath();
    }


    private void createPicPath() {
        String basePath = CommonUtils.getStoragePath(mContext, false);
        boolean isOk = CommonUtils.createFilePic(basePath);
        if (isOk) {
            Log.e("TAG", "create file success");
        }
    }

}
