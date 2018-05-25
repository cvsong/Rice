package com.cvsong.study.library.util;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

/**
 * AndroidManifest工具类
 * Created by chenweisong on 2018/5/24.
 */

public class AndroidManifestUtils {

    /**
     * 获取application标签中meta-data的参数值
     */
    public static String getMetaDataFromAppication(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取application标签中meta-data的int类型参数值
     */
    public static int getMetaDataIntFromAppication(Context context, String key, int defValue) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取application标签中meta-data的boolean类型参数值
     */
    public static boolean getMetaDataBooleanFromAppication(Context context, String key, boolean defValue) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getBoolean(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }


    /**
     * 获取activity标签中meta-data的string类型参数值
     */
    public static String getMetaDataFromActivity(Activity context, String key) {
        try {
            ActivityInfo info = context.getPackageManager().getActivityInfo(context.getComponentName(),
                    PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Service标签中meta-data的string类型参数值
     */
    public static String getMetaDataFromService(Context context, Class<? extends Service> clazz, String key) {
        try {
            ComponentName cn = new ComponentName(context, clazz);
            ServiceInfo info = context.getPackageManager().getServiceInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Receiver标签中meta-data的string类型参数值
     */
    public static String getMetaDataFromReceiver(Context context, Class<? extends BroadcastReceiver> clazz, String key) {
        try {
            ComponentName cn = new ComponentName(context, clazz);
            ActivityInfo info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
