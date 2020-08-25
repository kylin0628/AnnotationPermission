package com.kylin.annotationpermission.permission.aspectj;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.kylin.annotationpermission.permission.PermissionActivity;
import com.kylin.annotationpermission.permission.annotation.Permission;
import com.kylin.annotationpermission.permission.annotation.PermissionCancel;
import com.kylin.annotationpermission.permission.annotation.PermissionDenied;
import com.kylin.annotationpermission.permission.ipermission.PermissionCallback;
import com.kylin.annotationpermission.permission.utils.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Description:编译时期Aspect api会在编译同时通过@Aspect处理该类
 * @Auther: wangqi
 * CreateTime: 2020/8/25.
 */
@Aspect
public class PermissionAspect {

    @Pointcut("execution(@com.kylin.annotationpermission.permission.annotation.Permission * *(..)) && @annotation(permission)")
    public void AccessPoint(Permission permission) {
    }

    @Around("AccessPoint(permission)")
    public void ApproachMethod(final ProceedingJoinPoint point, Permission permission) throws Throwable {
        final Context context;
        final Object object = point.getThis();
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getContext();
        } else {
            point.proceed();
            return;
        }

        PermissionActivity.permission(context, permission.permissions(), permission.requestCode(), new PermissionCallback() {
            @Override
            public void permissionSucess() {
                try {
                    point.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void permissionCancel() {
                PermissionUtils.invokeAnnotation(object, PermissionCancel.class);
            }

            @Override
            public void permissionDenied() {
                PermissionUtils.invokeAnnotation(object, PermissionDenied.class);
                PermissionUtils.startAndroidSettings(context);
            }
        });
    }
}
