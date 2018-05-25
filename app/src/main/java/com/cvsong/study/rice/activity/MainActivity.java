package com.cvsong.study.rice.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.cvsong.study.library.net.download.VersionUpdateManager;
import com.cvsong.study.library.net.entity.HttpCallBack;
import com.cvsong.study.library.net.entity.Result;
import com.cvsong.study.library.util.app_tools.AppSpUtils;
import com.cvsong.study.library.util.permission.AppPermissionEntity;
import com.cvsong.study.library.util.permission.AppPermissionsManager;
import com.cvsong.study.library.util.permission.AppSettingsHolderActivity;
import com.cvsong.study.library.util.permission.IPermissionCallbacks;
import com.cvsong.study.library.util.permission.PermissionRequestCallback;
import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.util.utilcode.util.AppUtils;
import com.cvsong.study.rice.R;
import com.cvsong.study.rice.activity.start.StartGuideActivity;
import com.cvsong.study.rice.base.AppBaseActivity;
import com.cvsong.study.rice.entity.TestVersionUpdateEntity;
import com.cvsong.study.rice.manager.http.AppHttpManage;

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
            judgeIsNeedUpdate();//判断是否需要更新应用

        }
    };
    private VersionUpdateManager versionUpdateManager;


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
    }

    /**
     * 判断是否需要更新应用
     */
    private void judgeIsNeedUpdate() {

        //先网络请求获取版本更新信息
        //判断是否需要更新
        //如果需要更新则进行下载,不需要的话进行后面流程-->页面跳转

        AppHttpManage.checkVersionUpdate(this, new HttpCallBack<TestVersionUpdateEntity>() {
            @Override
            public void onSuccess(Result result, TestVersionUpdateEntity entity) {
                super.onSuccess(result, entity);
                int versionCode = entity.getVersionCode();
                String downloadUrl = entity.getDownloadUrl();
                String updateDesc = entity.getUpdateDesc();
                boolean haveToUpdate = entity.isHaveToUpdate();//是否必须更新

                int appVersionCode = AppUtils.getAppVersionCode();
                if (versionCode > appVersionCode && downloadUrl != null) {//需要进行版本更新
                    //弹窗提示进行版本更新
                    versionUpdateManager = new VersionUpdateManager(activity);
                    versionUpdateManager.makeVersionUpdate(versionCode, downloadUrl, updateDesc, haveToUpdate, new VersionUpdateManager.VersionUpdateCallback() {
                        @Override
                        public void onNextStep() {//下一步
                            judgeIsSkipGuidePage(); //判断是否跳转引导页
                        }
                    });


                    return;
                }
                //不需要版本更新
                judgeIsSkipGuidePage(); //判断是否跳转引导页

            }
        });


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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (versionUpdateManager!=null) {
            versionUpdateManager.close();//注销
        }

    }
}
