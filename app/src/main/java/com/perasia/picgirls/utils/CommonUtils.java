package com.perasia.picgirls.utils;


import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.text.TextUtils;

import com.perasia.picgirls.Config;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    /**
     * is_removale
     * false: in storage
     * true: out storage
     */
    public static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean createFilePic(String basePath) {
        boolean isOk = false;

        if (TextUtils.isEmpty(basePath)) {
            return false;
        }

        if (getSdCardState()) {
            String path = basePath + File.separator + Config.PIC_FILE;
            File file = new File(path);
            if (!file.exists()) {
                isOk = file.mkdirs();
            }
        }

        return isOk;
    }

    public static boolean getSdCardState() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPicFileBasePath(Context context) {
        return getStoragePath(context, false) + File.separator + Config.PIC_FILE;
    }

}
