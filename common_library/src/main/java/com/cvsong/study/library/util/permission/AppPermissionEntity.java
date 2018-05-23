package com.cvsong.study.library.util.permission;

import java.io.Serializable;

/**
 * 权限实体对象类
 * Created by chenweisong on 2018/5/23.
 */

public class AppPermissionEntity implements Serializable{

    private String permissionId;//权限ID
    private String permissionName;//权限名称


    public AppPermissionEntity() {
    }

    public AppPermissionEntity(String permissionId, String permissionName) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }


    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
