package com.cvsong.study.library.util.permission;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.cvsong.study.library.util.utilcode.util.ActivityUtils;
import com.cvsong.study.library.wiget.CustomDialogManager;

import java.util.List;

/**
 * 权限申请结果实现类
 * Created by chenweisong on 2018/5/23.
 */

public class PermissionRequestCallback implements IPermissionCallbacks {

    @Override
    public void onAllPermissionsGranted(int requestCode, @NonNull List<AppPermissionEntity> appPermissions) {

    }

    @Override
    public void onSomePermissionsNotGranted(final Context context, int requestCode, @NonNull List<AppPermissionEntity> grantedPermissions, @NonNull List<AppPermissionEntity> deniedPermissions) {
        StringBuilder sb = new StringBuilder();
        for (AppPermissionEntity entity : deniedPermissions) {
            sb.append(entity.getPermissionName() + " ");
        }
        String permissionNames = sb.toString().trim();
        CustomDialogManager.newBuilder(context)
                .titleText("温馨提示")
                .contentText(String.format("本应用的使用必须拥有%s的权限,请到应用设置开通.", permissionNames))
                .negativeButtonText("取消")
                .negativeListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (context instanceof Activity) {
                            ActivityUtils.finishActivity(context.getClass());
                        }
                    }
                })
                .positiveButtonText("设置")
                .positiveListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (context instanceof Activity) {
                            Activity activity = (Activity) context;
                            AppSettingsHolderActivity.start(activity, AppSettingsHolderActivity.RC_APP_SETTING);
                        }

                    }
                })
                .build().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
