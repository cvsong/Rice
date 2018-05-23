package com.cvsong.study.rice.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.cvsong.study.library.util.app_tools.AppSpUtils;
import com.cvsong.study.library.util.permission.AppPermissionEntity;
import com.cvsong.study.library.util.permission.AppPermissionsManager;
import com.cvsong.study.library.util.permission.AppSettingsHolderActivity;
import com.cvsong.study.library.util.permission.IPermissionCallbacks;
import com.cvsong.study.library.util.permission.PermissionRequestCallback;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.activity.start.StartGuideActivity;
import com.cvsong.study.rice.base.AppBaseActivity;

import java.util.List;

import butterknife.BindView;


public class MainActivity extends AppBaseActivity {


    @BindView(R.id.iv_img)
    ImageView ivImg;

    private AppPermissionEntity[] perms;


    private IPermissionCallbacks permissionCallback = new PermissionRequestCallback() {

        @Override
        public void onAllPermissionsGranted(int requestCode, @NonNull List<AppPermissionEntity> appPermissions) {
            super.onAllPermissionsGranted(requestCode, appPermissions);
            judgeIsSkipGuidePage(); //判断是否跳转引导页
        }


        //        @Override
//        public void onSomePermissionsNotGranted(Context context, int requestCode, @NonNull List<AppPermissionEntity> grantedPermissions, @NonNull List<AppPermissionEntity> deniedPermissions) {
//            StringBuilder sb = new StringBuilder();
//            for (AppPermissionEntity entity : deniedPermissions) {
//                sb.append(entity.getPermissionName() + " ");
//            }
//            String permissionNames = sb.toString().trim();
//            CustomDialogManager.newBuilder(MainActivity.this)
//                    .titleText("温馨提示")
//                    .contentText(String.format("本应用的使用必须拥有%s的权限,请到应用设置开通.", permissionNames))
//                    .negativeButtonText("取消")
//                    .negativeListener(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            ActivityUtils.finishActivity(MainActivity.class);
//                        }
//                    })
//                    .positiveButtonText("设置")
//                    .positiveListener(new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            AppSettingsHolderActivity.start(MainActivity.this, RC_APP_SETTING);
//                        }
//                    })
//                    .build().show();
//        }
    };


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
    public void requestAppNeedPermissions() {
        perms = new AppPermissionEntity[]{new AppPermissionEntity(Manifest.permission.CAMERA, "相机"), new AppPermissionEntity(Manifest.permission.WRITE_EXTERNAL_STORAGE, "读写SD卡")};
        AppPermissionsManager.requestPermissions(this, permissionCallback, perms);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppPermissionsManager.onRequestPermissionsResult(this, requestCode, perms, grantResults, permissionCallback);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsHolderActivity.RC_APP_SETTING) {//跳转设置页面
            requestAppNeedPermissions();
        }
    }


    /**
     * 判断是否跳转引导页
     */
    private void judgeIsSkipGuidePage() {
        //是否再次打开
        boolean isOpenAgain = AppSpUtils.getInstance().getBoolean(AppSpUtils.IS_OPEN_AGAIN);
        ActivityUtils.finishActivity(MainActivity.class);//结束当前页面
        //第一次启动应用--->启动引导页面否则跳转主页面
        ActivityUtils.startActivity(isOpenAgain ? HomeActivity.class : StartGuideActivity.class);

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
