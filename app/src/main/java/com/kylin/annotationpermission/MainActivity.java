package com.kylin.annotationpermission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kylin.annotationpermission.permission.annotation.Permission;
import com.kylin.annotationpermission.permission.annotation.PermissionCancel;
import com.kylin.annotationpermission.permission.annotation.PermissionDenied;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Permission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode = 100)
    public void requestPermission(View view) {
        Toast.makeText(this, "权限请求成功", Toast.LENGTH_LONG).show();
    }

    @PermissionCancel
    public void requestPermissionCancel() {
        Toast.makeText(this, "权限请求取消", Toast.LENGTH_LONG).show();
    }

    @PermissionDenied
    public void requestPermissionDenied() {
        Toast.makeText(this, "权限请求被拒绝", Toast.LENGTH_LONG).show();
    }
}