package com.cvsong.study.library.util.permission;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.app.Fragment;

import com.cvsong.study.library.util.permission.helper.PermissionHelper;

import java.util.Arrays;


/**
 * 权限请求类
 */
public final class PermissionRequest {

    private final PermissionHelper permissionHelper;
    private final AppPermissionEntity[] perms;
    private final int requestCode;

    private PermissionRequest(Builder builder) {
        permissionHelper = builder.permissionHelper;
        perms = builder.perms;
        requestCode = builder.requestCode;
    }


    @NonNull
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public PermissionHelper getHelper() {
        return permissionHelper;
    }

    @NonNull
    public AppPermissionEntity[] getPerms() {
        return perms;
    }

    public int getRequestCode() {
        return requestCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionRequest request = (PermissionRequest) o;
        return Arrays.equals(this.perms, request.perms) && requestCode == request.requestCode;
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(perms);
        result = 31 * result + requestCode;
        return result;
    }


    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private PermissionHelper permissionHelper;
        private AppPermissionEntity[] perms;
        private int requestCode;

        private Builder() {
        }

        public Builder permissionHelper(@NonNull Activity host) {
            this.permissionHelper = PermissionHelper.newInstance(host);
            return this;
        }

        public Builder permissionHelper(@NonNull Fragment host) {
            this.permissionHelper = PermissionHelper.newInstance(host);
            return this;
        }

        public Builder permissionHelper(@NonNull android.app.Fragment host) {
            this.permissionHelper = PermissionHelper.newInstance(host);
            return this;
        }


        public Builder perms(AppPermissionEntity[] perms) {
            this.perms = perms;
            return this;
        }

        public Builder requestCode(int perms) {
            requestCode = perms;
            return this;
        }

        public PermissionRequest build() {
            return new PermissionRequest(this);
        }
    }

}
