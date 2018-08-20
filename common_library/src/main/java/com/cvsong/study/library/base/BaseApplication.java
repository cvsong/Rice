package com.cvsong.study.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.cvsong.study.library.util.AndroidManifestUtils;
import com.cvsong.study.library.util.CommonUtils;
import com.cvsong.study.library.util.app_tools.AppConfig;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.library.util.utilcode.util.Utils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * 顶级BaseApplication
 */
public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();


    //Activity生命周期回调
    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LogUtils.d(TAG, "onActivityCreated() called with: activity = [" + activity + "], savedInstanceState = [" + savedInstanceState + "]");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogUtils.d(TAG, "onActivityStarted() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            //友盟App使用时长统计
            MobclickAgent.onResume(activity);
            LogUtils.d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //友盟App使用时长统计
            MobclickAgent.onPause(activity);
            LogUtils.d(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogUtils.d(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogUtils.d(TAG, "onActivitySaveInstanceState() called with: activity = [" + activity + "], outState = [" + outState + "]");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtils.d(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
        }
    };




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//解决64K方法数限制
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);//初始化全局Context以及Activity堆栈管理

        //注册Activity监听
        registerActivityLifecycleCallbacks(mCallbacks);
        initUmentAnalyze();//初始化友盟统计

    }

    /**
     * 初始化友盟统计
     */
    private void initUmentAnalyze() {

        //初始化友盟统计Common库 参二:AppKey 参三:Channel 参四:设备类型 参五:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
        UMConfigure.init(this, null, null, UMConfigure.DEVICE_TYPE_PHONE, null);

    }




}

