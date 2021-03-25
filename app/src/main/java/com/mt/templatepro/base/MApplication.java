package com.mt.templatepro.base;

import android.app.Application;
import android.content.Context;

import com.firefly1126.permissionaspect.PermissionCheckSDK;
import com.liys.doubleclicklibrary.helper.ViewDoubleHelper;

public class MApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        //注册aop权限申请框架
        context = this;
        PermissionCheckSDK.init(this);
        //同意处理重复点击，默认是1s
        ViewDoubleHelper.init(this);
        //日志控件，如果是debug模式打印出来，pro可以根据配置是否记录在日志文件中

    }
    public static Context getContext(){
        return context;
    }

}
