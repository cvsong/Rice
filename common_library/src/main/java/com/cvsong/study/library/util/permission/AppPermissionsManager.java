package com.cvsong.study.library.util.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.cvsong.study.library.util.permission.helper.PermissionHelper;
import com.cvsong.study.library.util.utilcode.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用权限管理类
 * Created by chenweisong on 2018/5/23.
 */

public class AppPermissionsManager {

    private static final String TAG = AppPermissionsManager.class.getSimpleName();
    public static final int RC_APP_NEED_PERMISSIONS = 123;

    /**
     * 判断是否拥有权限
     *
     * @param context
     * @param perms
     * @return
     */
    public static boolean hasPermissions(@NonNull Context context,
                                         @Size(min = 1) @NonNull AppPermissionEntity[] perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//API版本小于23即6.0时
            Log.w(TAG, "hasPermissions: 当API版本小于23即6.0时,默认返回true");
            //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
            // 但是，如果用户关闭了你申请的权限(如下图，在安装的时候，将一些权限关闭了)，ActivityCompat.checkSelfPermission()则可能会导致程序崩溃(java.lang.RuntimeException: Unknown exception code: 1 msg null)，
            //所以当判断API小于23时就认为拥有所申请的权限
            // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。permissionGrant.onPermissionGranted(requestCode);

            // 警告!!!此处必须返回true
            return true;
        }

        if (context == null) {
            throw new IllegalArgumentException("检查是否拥有所请求的权限context不能为null");
        }

        context = Utils.getApp();//TODO

        for (AppPermissionEntity perm : perms) {
            //检查是否拥有这个权限
            if (ContextCompat.checkSelfPermission(context, perm.getPermissionId()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }


    /**
     * 请求权限
     *
     * @param host
     * @param perms
     */
    public static void requestPermissions(@NonNull Activity host, @NonNull IPermissionCallbacks permissionCallback, @Size(min = 1) @NonNull AppPermissionEntity... perms) {
        PermissionRequest permissionRequest = PermissionRequest.newBuilder()
                .requestCode(RC_APP_NEED_PERMISSIONS)
                .permissionHelper(host)
                .perms(perms)
                .build();
        requestPermissions(permissionRequest, permissionCallback);
    }

    /**
     * 请求权限
     *
     * @param host
     * @param perms
     */
    public static void requestPermissions(@NonNull Fragment host, @NonNull IPermissionCallbacks permissionCallback, @Size(min = 1) @NonNull AppPermissionEntity... perms) {
        PermissionRequest permissionRequest = PermissionRequest.newBuilder()
                .requestCode(RC_APP_NEED_PERMISSIONS)
                .permissionHelper(host)
                .perms(perms)
                .build();
        requestPermissions(permissionRequest, permissionCallback);
    }

    /**
     * 请求权限
     *
     * @param host
     * @param perms
     */
    public static void requestPermissions(@NonNull android.app.Fragment host, @NonNull IPermissionCallbacks permissionCallback, @Size(min = 1) @NonNull AppPermissionEntity... perms) {
        PermissionRequest permissionRequest = PermissionRequest.newBuilder()
                .requestCode(RC_APP_NEED_PERMISSIONS)
                .permissionHelper(host)
                .perms(perms)
                .build();
        requestPermissions(permissionRequest, permissionCallback);
    }


    /**
     * 请求权限
     *
     * @param request
     */
    public static void requestPermissions(PermissionRequest request, @NonNull IPermissionCallbacks permissionCallback) {

        // 校验是否拥有所申请的权限
        if (hasPermissions(request.getHelper().getContext(), request.getPerms())) {
            //通知已全部拥有所请求的权限
            notifyAlreadyHasPermissions(permissionCallback, request);
            return;
        }

        // 请求权限
        request.getHelper().requestPermissions(
                request.getRequestCode(),
                request.getPerms());
    }


    /**
     * 通知已拥有所请求的权限
     *
     * @param permissionCallback
     * @param request
     */
    private static void notifyAlreadyHasPermissions(@NonNull IPermissionCallbacks permissionCallback,PermissionRequest request) {
        AppPermissionEntity[] perms = request.getPerms();
        int[] grantResults = new int[perms.length];
        for (int i = 0; i < perms.length; i++) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED;
        }

        onRequestPermissionsResult(request.getHelper().getContext(),request.getRequestCode(), perms, grantResults, permissionCallback);
    }


    /**
     * 请求权限结果
     *
     * @param context
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param permissionCallback
     */
    public static void onRequestPermissionsResult(Context context, int requestCode,
                                                  @NonNull AppPermissionEntity[] permissions,
                                                  @NonNull int[] grantResults,
                                                  @NonNull IPermissionCallbacks permissionCallback) {
        // 对所请求的权限中,允许和未允许的进行收集
        List<AppPermissionEntity> granted = new ArrayList<>();//允许集合
        List<AppPermissionEntity> denied = new ArrayList<>();//禁止集合
        for (int i = 0; i < permissions.length; i++) {
            AppPermissionEntity perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // 所有申请的权限都被允许
        if (!granted.isEmpty() && denied.isEmpty()) {
            permissionCallback.onAllPermissionsGranted(requestCode, denied);
        } else {
            permissionCallback.onSomePermissionsNotGranted(context,requestCode, granted, denied);
        }

    }




    // ============================================================================
    // 不推荐使用的方法
    // ============================================================================

    /**
     * 判断是否一些权限被永久禁止-->依据shouldShowRequestPermissionRationale()方法,但是该方法对于一些手机不支持,比如小米，小米4
     */
    @Deprecated
    public static boolean somePermissionPermanentlyDenied(@NonNull Activity host,
                                                          @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(deniedPermissions);
    }

    /**
     * 判断是否一些权限被永久禁止-->依据shouldShowRequestPermissionRationale()方法,但是该方法对于一些手机不支持,比如小米，小米4
     */
    @Deprecated
    public static boolean somePermissionPermanentlyDenied(@NonNull Fragment host,
                                                          @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(deniedPermissions);
    }

    /**
     * 判断是否一些权限被永久禁止-->依据shouldShowRequestPermissionRationale()方法,但是该方法对于一些手机不支持,比如小米，小米4
     */
    @Deprecated
    public static boolean somePermissionPermanentlyDenied(@NonNull android.app.Fragment host,
                                                          @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(deniedPermissions);
    }


}
