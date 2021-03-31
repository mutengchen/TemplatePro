package com.mt.templatepro;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.hujiang.permissiondispatcher.NeedPermission;
import com.mt.mCamView.CameraSurfaceViewActivity;
import com.mt.templatepro.base.BaseActivity;

import java.security.Permission;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setLayoutId() {
        openCamera();
        return R.layout.camera_view;

    }

    @NeedPermission(permissions = {Manifest.permission.CAMERA})
    private void openCamera(){
        Intent intent = new Intent(this, CameraSurfaceViewActivity.class);
        startActivity(intent);

    }
}