package com.mt.templatepro.base;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import com.hujiang.permissiondispatcher.NeedPermission;

import androidx.annotation.Nullable;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId();
        //申请存储权限，这是app必备的权限，其他的动态去申请
        requestPermission();
    }

    public abstract int setLayoutId();

    @NeedPermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void requestPermission(){

    }
}
