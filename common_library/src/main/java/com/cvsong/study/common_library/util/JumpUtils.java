package com.cvsong.study.common_library.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 界面跳转的辅助工具类
 */

public class JumpUtils {
    //启动目标Activity
    public static void launch(Context caller, Class<?> target) {
        launch(caller, null, target);
    }

    //启动目标Activity，然后关闭自身
    public static void launchWithFinishSelf(Context caller, Class<?> target) {
        launch(caller, null, target);
        if(caller instanceof Activity)
        {
            ((Activity) caller).finish();
        }
    }

    //启动目标Activity，带入数据
    public static void launch(Context caller, Bundle bundle, Class<?> target) {
        caller.startActivity(createIntentForLaunch(caller, bundle, target));
    }

    //启动目标Activity，带入数据，然后关闭自身
    public static void launchWithFinishSelf(Context caller, Bundle bundle, Class<?> target) {
        caller.startActivity(createIntentForLaunch(caller, bundle, target));
        if(caller instanceof Activity)
        {
            ((Activity) caller).finish();
        }
    }

    //启动目标Activity，关闭时需返回结果
    public static void launchNeedResult(Activity caller, Bundle bundle, Class<?> target, int requestCode) {
        caller.startActivityForResult(createIntentForLaunch(caller, bundle, target), requestCode);
    }

    //关闭当前界面，回传结果
    public static void finishWithResult(Activity caller, Bundle bundle, Class<?> target, int resultCode) {
        caller.setResult(resultCode, createIntentForLaunch(caller, bundle, target));
        caller.finish();
    }

    private static Intent createIntentForLaunch(Context caller, Bundle bundle, Class<?> target) {
        Intent intent = new Intent();
        if(null != bundle) {
            intent.putExtras(bundle);
        }
        intent.setClass(caller, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);

        return intent;
    }
}
