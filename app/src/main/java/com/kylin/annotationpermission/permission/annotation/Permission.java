package com.kylin.annotationpermission.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.kylin.annotationpermission.permission.utils.PermissionContant.REQUEST_CODE_DEFALUT;

/**
 * @Description:权限请求和允许
 * @Auther: wangqi
 * CreateTime: 2020/8/25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String[] permissions();

    int requestCode() default REQUEST_CODE_DEFALUT;
}
