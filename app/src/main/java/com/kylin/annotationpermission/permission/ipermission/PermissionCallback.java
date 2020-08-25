package com.kylin.annotationpermission.permission.ipermission;

/**
 * @Description:
 * @Auther: wangqi
 * CreateTime: 2020/8/25.
 */
public interface PermissionCallback {
    void permissionSucess();

    void permissionCancel();

    void permissionDenied();
}
