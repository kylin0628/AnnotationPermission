package com.kylin.annotationpermission.permission;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kylin.annotationpermission.permission.ipermission.PermissionCallback;
import com.kylin.annotationpermission.permission.utils.PermissionUtils;

import static com.kylin.annotationpermission.permission.utils.PermissionContant.KEY_PERMISSION;
import static com.kylin.annotationpermission.permission.utils.PermissionContant.KEY_PERMISSION_CODE;
import static com.kylin.annotationpermission.permission.utils.PermissionContant.REQUEST_CODE_DEFALUT;

/**
 * @Description:
 * @Auther: wangqi
 * CreateTime: 2020/8/25.
 */
public class PermissionActivity extends AppCompatActivity {

    private String[] permissions;
    private int requestCode = REQUEST_CODE_DEFALUT;
    private static PermissionCallback callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new View(this);
        view.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        setContentView(view);
        //获取权限和code
        Bundle intent = getIntent().getExtras();
        if (intent == null) {
            return;
        }
        if (intent.containsKey(KEY_PERMISSION)) {
            permissions = intent.getStringArray(KEY_PERMISSION);
        }
        if (intent.containsKey(KEY_PERMISSION_CODE)) {
            requestCode = intent.getInt(KEY_PERMISSION_CODE, REQUEST_CODE_DEFALUT);
        }
        if ((permissions == null || permissions.length == 0) && requestCode < 0) {
            finish();
            return;
        }
        //判断是否已经请求，已经请求成功不再重复请求
        if (permissions != null && PermissionUtils.hasPermissionRequest(this, permissions)) {
            callback.permissionSucess();
            finish();
        }
        //发起权限请求
        if (permissions != null) {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtils.requestPermissionSuccess(grantResults)) {
            callback.permissionSucess();
        } else if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
            callback.permissionDenied();
        } else {
            callback.permissionCancel();
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        //去除关闭动画
        overridePendingTransition(0, 0);
    }

    public static void permission(Context context, String[] permissions, int requestCode, PermissionCallback listener) {
        callback = listener;
        Intent intent = new Intent(context, PermissionActivity.class);
        Bundle bundle = new Bundle();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        bundle.putStringArray(KEY_PERMISSION, permissions);
        bundle.putInt(KEY_PERMISSION_CODE, requestCode);
        intent.putExtras(bundle);
        context.startActivity(intent);
        //todo 去除启动动画，当fragment启动的时候未解决
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.overridePendingTransition(0, 0);
        }
    }
}
