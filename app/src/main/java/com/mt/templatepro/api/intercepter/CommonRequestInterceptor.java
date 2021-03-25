package com.mt.templatepro.api.intercepter;


import com.mt.component_util.utils.VersionUtils;
import com.mt.templatepro.api.exception.UnknownAPIVersionException;
import com.mt.templatepro.api.exception.VersionTooOldException;
import com.mt.templatepro.base.MApplication;
import com.mt.templatepro.utils.SharePreferencesUtils;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Authorization", "Bearer "+ SharePreferencesUtils.getInstance().get("token","") );
        // 构造请求头
        Request request = builder.build();

        // 获取返回值
        Response response = chain.proceed(request);
        // 新建好正则
        String version_regex = "^[0-9a-zA-Z]+\\.[0-9a-zA-Z]+\\.[0-9a-zA-Z]+$";
        // 获取版本号
        String version = response.header("version");
        String our_version = VersionUtils.getAppVersionName(MApplication.getContext());
        // TODO 判断是否为更新版本的接口，更新版本的接口不需要拦截，需要解耦
        if(request.url().toString().indexOf("/api/currentversion") == -1){
            // 判断是否有版本号，并且版本号符合x.x.x(^[0-9a-zA-Z]+\.[0-9a-zA-Z]+\.[0-9a-zA-Z]+$)
            if(version==null || !Pattern.matches(version_regex,version) || !Pattern.matches(version_regex,our_version))
                throw new UnknownAPIVersionException();
            // 判断版本号是否比较旧，只需匹配前两位x.x
            String[] version_list = version.split("\\.");
            String[] our_version_list = our_version.split("\\.");
            if(!version_list[0].equals(our_version_list[0]) || !version_list[1].equals(our_version_list[1]))
                throw new VersionTooOldException();
        }
        return response;
    }
}
