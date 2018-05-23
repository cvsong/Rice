package com.cvsong.study.library.util.permission;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.List;

/**
 * 权限申请结果回调
 */
public interface IPermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {


    //申请的权限全部允许
    void onAllPermissionsGranted(int requestCode, @NonNull List<AppPermissionEntity> appPermissions);

    //部分权限没有允许
    void onSomePermissionsNotGranted(Context context, int requestCode, @NonNull List<AppPermissionEntity> grantedPermissions, @NonNull List<AppPermissionEntity> deniedPermissions);

}