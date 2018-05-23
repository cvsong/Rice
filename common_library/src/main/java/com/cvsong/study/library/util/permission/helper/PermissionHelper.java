package com.cvsong.study.library.util.permission.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cvsong.study.library.util.permission.AppPermissionEntity;

import java.util.List;

/**
 * 基于'host' (Fragment, Activity, etc)去请求权限
 * Delegate class to make permission calls based on the 'host' (Fragment, Activity, etc).
 */
public abstract class PermissionHelper<T> {

    private T mHost;

    @NonNull
    public static PermissionHelper<? extends Activity> newInstance(Activity host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionsHelper<>(host);
        }

        if (host instanceof AppCompatActivity)
            return new AppCompatActivityPermissionHelper((AppCompatActivity) host);
        else {
            return new ActivityPermissionHelper(host);
        }
    }

    @NonNull
    public static PermissionHelper<Fragment> newInstance(Fragment host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionsHelper<>(host);
        }

        return new SupportFragmentPermissionHelper(host);
    }

    @NonNull
    public static PermissionHelper<android.app.Fragment> newInstance(android.app.Fragment host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionsHelper<>(host);
        }

        return new FrameworkFragmentPermissionHelper(host);
    }

    // ============================================================================
    // Public concrete methods
    // ============================================================================

    public PermissionHelper(@NonNull T host) {
        mHost = host;
    }


    /**
     * 申请权限
     *
     * @param requestCode
     * @param perms
     */
    public void requestPermissions(int requestCode, @NonNull AppPermissionEntity... perms) {

        String[] permissions = new String[perms.length];
        for (int i = 0; i < perms.length; i++) {
            permissions[i] = perms[i].getPermissionId();
        }

        //不需要显示解释--->直接申请权限
        directRequestPermissions(requestCode, permissions);

           /* 关于shouldShowRequestPermissionRationale()方法：

           Android原生系统中，如果第二次弹出权限申请的对话框，会出现“以后不再弹出”的提示框，
           如果用户勾选了，你再申请权限，则shouldShowRequestPermissionRationale返回true，意思是说要给用户一个 解释，告诉用户为什么要这个权限。
           然而，在实际开发中，需要注意的是，很多手机对原生系统做了修改，比如小米，小米4的6.0的shouldShowRequestPermissionRationale 就一直返回false，
           而且在申请权限时，如果用户选择了拒绝，则不会再弹出对话框了。。。。
           所以说这个地方有坑，我的解决方法是，在回调里面处理，如果用户拒绝了这个权限，则打开本应用信息界面，由用户自己手动开启这个权限。*/

        //判断是否需要展示申请权限的解释
//        if (shouldShowRationale(permissions)) {
//            showRequestPermissionRationale(requestCode, perms);//显示申请权限的解释
//        } else {
//            //不需要显示解释--->直接申请权限
//            directRequestPermissions(requestCode, permissions);
//        }
    }

    /**
     * 是否需要展示申请权限的解释
     *
     * @param perms
     * @return
     */
    private boolean shouldShowRationale(@NonNull String... perms) {
        for (String perm : perms) {
            //判断是否需要向用户解释为何申请权限
            if (shouldShowRequestPermissionRationale(perm)) {
                return true;
            }
        }
        return false;
    }


    public boolean somePermissionPermanentlyDenied(@NonNull List<String> perms) {
        for (String deniedPermission : perms) {
            if (permissionPermanentlyDenied(deniedPermission)) {
                return true;
            }
        }

        return false;
    }

    public boolean permissionPermanentlyDenied(@NonNull String perms) {
        return !shouldShowRequestPermissionRationale(perms);
    }

    public boolean somePermissionDenied(@NonNull String... perms) {
        return shouldShowRationale(perms);
    }

    @NonNull
    public T getHost() {
        return mHost;
    }

    // ============================================================================
    // Public abstract methods
    // ============================================================================

    //直接申请权限
    public abstract void directRequestPermissions(int requestCode, @NonNull String... perms);

    //显示申请权限的解释-->不建议调用
    public abstract boolean shouldShowRequestPermissionRationale(@NonNull String perm);


    public abstract Context getContext();

}
