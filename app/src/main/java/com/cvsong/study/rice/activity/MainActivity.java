package com.cvsong.study.rice.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cvsong.study.library.util.app_tools.AppSpUtils;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.util.utilcode.util.ToastUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.activity.start.StartGuideActivity;
import com.cvsong.study.rice.base.AppBaseActivity;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppBaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final int RC_APP_NEED_PERMISSIONS = 123;
    private static final String[] NEED_PERMISSIONS =
            {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @BindView(R.id.iv_img)
    ImageView ivImg;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        titleView.setTitleVisibility(View.GONE);
        ivImg.setOnClickListener(this);

    }


    @Override
    public void loadData() {

        //App6.0权限申请
        requestAppNeedPermissions();

        //TODO 更新判断


    }


    /**
     * 请求App所需权限
     */
    @AfterPermissionGranted(RC_APP_NEED_PERMISSIONS)
    public void requestAppNeedPermissions() {
        if (EasyPermissions.hasPermissions(this, NEED_PERMISSIONS)) {//判断是否拥有所需权限
            //判断是否跳转引导页
            judgeIsSkipGuidePage();

        } else {//请求权限
            EasyPermissions.requestPermissions(
                    this, "需要使用相机和读写SD卡权限",
                    RC_APP_NEED_PERMISSIONS, NEED_PERMISSIONS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 再次请求权限时已有授权的权限回调
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//        ToastUtils.showShort("onPermissionsGranted");
    }

    /**
     * 权限被禁止时回调
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        //权限被禁止-->

        judgeIsSkipGuidePage();


        //判断权限是否被永久禁止---
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {

            //弹出跳转设置页面的弹窗-->可以自定义
            new AppSettingsDialog.Builder(this)
                    .setTitle("温馨提示")//设置弹窗标题
                    .setRationale("被永久禁止的权限需要到设置中恢复...")//设置弹窗内容
                    .build().show();
        }
    }




    /**
     * 判断是否跳转引导页
     */
    private void judgeIsSkipGuidePage() {
        //是否再次打开
        boolean isOpenAgain = AppSpUtils.getInstance().getBoolean(AppSpUtils.IS_OPEN_AGAIN);
        if (!isOpenAgain) {//第一次启动应用--->启动引导页面
            ActivityUtils.finishActivity(MainActivity.class);
            ActivityUtils.startActivity(StartGuideActivity.class);
        } else {//再次启动-->主页面
            ActivityUtils.finishActivity(MainActivity.class);
            ActivityUtils.startActivity(HomeActivity.class);
        }
    }


    @Override
    protected void onWidgetClick(View view) {
        super.onWidgetClick(view);
        switch (view.getId()) {
            case R.id.iv_img:

                break;
        }
    }


}
