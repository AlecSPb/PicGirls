package com.perasia.picgirls.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {

    private static SharedPreferences sharedPreferences;

    private static String CONFIG = "content";

    public static void saveStringData(Context context, String key, String value) {

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getStringData(Context context, String key, String defValue) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG,
                    Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, defValue);
    }

}
