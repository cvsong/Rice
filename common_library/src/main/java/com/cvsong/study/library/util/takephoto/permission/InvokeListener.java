package com.cvsong.study.library.util.takephoto.permission;


import com.cvsong.study.library.util.takephoto.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
