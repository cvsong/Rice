package com.cvsong.study.library.util.permission.helper;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;


/**
 * Implementation of {@link PermissionHelper} for Support Library host classes.
 */
public abstract class BaseSupportPermissionsHelper<T> extends PermissionHelper<T> {

    private static final String TAG = "BSPermissionsHelper";

    public BaseSupportPermissionsHelper(@NonNull T host) {
        super(host);
    }

    public abstract FragmentManager getSupportFragmentManager();
//
//
//    /**
//     * 显示申请权限解释的弹窗--->可自定义
//     *
//     * @param requestCode
//     * @param perms
//     */
//    @Override
//    public void showRequestPermissionRationale(int requestCode, @NonNull AppPermissionEntity... perms) {
//
//        FragmentManager fm = getSupportFragmentManager();
//
//        // Check if fragment is already showing
//        Fragment fragment = fm.findFragmentByTag(RationaleDialogFragmentCompat.TAG);
//        if (fragment instanceof RationaleDialogFragmentCompat) {
//            Log.d(TAG, "Found existing fragment, not showing rationale.");
//            return;
//        }
//
//        RationaleDialogFragmentCompat
//                .newInstance(requestCode, perms)
//                .showAllowingStateLoss(fm, RationaleDialogFragmentCompat.TAG);
//    }
}
