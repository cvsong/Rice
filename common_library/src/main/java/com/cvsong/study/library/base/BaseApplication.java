package com.cvsong.study.library.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.cvsong.study.library.andfix.AndFixUtils;
import com.cvsong.study.library.util.utilcode.util.AppUtils;
import com.cvsong.study.library.util.utilcode.util.LogUtils;
import com.cvsong.study.library.util.utilcode.util.Utils;

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
            LogUtils.d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityPaused(Activity activity) {
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

        //初始化AndFixManager
        initAndFix();
    }



    /**
     * 初始化AndFixManager
     */
    private void initAndFix() {
        AndFixUtils.getInstance().initAndFix(this, AppUtils.getAppVersionName());
    }


}

