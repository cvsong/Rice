package com.cvsong.study.library.util.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;


/**
 * 应用设置过渡页面
 * Created by chenweisong on 2018/5/23.
 */

public class AppSettingsHolderActivity extends AppCompatActivity {

    public static final int RC_APP_SETTING = 101;


    /**
     * 打开页面
     *
     * @param activity
     * @param requestCode
     */
    public static void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, AppSettingsHolderActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startAppSettingActivity();//跳转App设置页面
    }

    /**
     * 跳转App设置页面
     */
    private void startAppSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, 9527);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(resultCode, data);
        finish();
    }


}
