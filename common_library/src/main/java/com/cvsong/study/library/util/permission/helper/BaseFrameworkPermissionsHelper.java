package com.cvsong.study.library.util.permission.helper;

import android.app.FragmentManager;
import android.support.annotation.NonNull;


/**
 * Implementation of {@link PermissionHelper} for framework host classes.
 */
public abstract class BaseFrameworkPermissionsHelper<T> extends PermissionHelper<T> {

    private static final String TAG = "BFPermissionsHelper";

    public BaseFrameworkPermissionsHelper(@NonNull T host) {
        super(host);
    }

    public abstract FragmentManager getFragmentManager();

//    /**
//     * 显示申请权限解释的弹窗--->可自定义
//     *
//     * @param requestCode
//     * @param perms
//     */
//    @Override
//    public void showRequestPermissionRationale(int requestCode, @NonNull AppPermissionEntity... perms) {
//        FragmentManager fm = getFragmentManager();
//
//        // Check if fragment is already showing
//        Fragment fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG);
//        if (fragment instanceof RationaleDialogFragment) {
//            Log.d(TAG, "Found existing fragment, not showing rationale.");
//            return;
//        }
//
//        RationaleDialogFragment.newInstance(requestCode, perms)
//                .showAllowingStateLoss(fm, RationaleDialogFragment.TAG);
//    }
}
