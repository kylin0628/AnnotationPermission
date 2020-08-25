package com.kylin.annotationpermission.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.kylin.annotationpermission.permission.utils.PermissionContant.REQUEST_CODE_DEFALUT;

/**
 * @Description:权限被拒绝会下次再次询问 询问
 * @Auther: wangqi
 * CreateTime: 2020/8/25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionDenied {
    int requestCode() default REQUEST_CODE_DEFALUT;
}
