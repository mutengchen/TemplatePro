package com.mt.templatepro.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.util.Log;


import com.mt.templatepro.api.intercepter.CommonRequestInterceptor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * okHttpclient 模式的单例类
 * Created by cmt on 2017/9/28.
 */

public class OkHttpUtils {
    public final static int CONNECT_TIMEOUT = 15;
    public final static int READ_TIMEOUT = 15;
    public final static int WRITE_TIMEOUT = 15;

    private static OkHttpClient client = null;
    private static HttpLoggingInterceptor httpLoggingInterceptor;

    private OkHttpUtils() {

    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (OkHttpUtils.class) {
                if (client == null)
                    client = new OkHttpClient().newBuilder().build();
            }
        }
        return client;
    }
    public static RequestBody buildReqeuestByHashmap(HashMap<String,String> hashMap){
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(Map.Entry<String,String> entry:hashMap.entrySet()){
            //上传文件
            body.addFormDataPart(entry.getKey(),entry.getValue());
        }

        return body.build();
    }
    private static String TAG = "OkHttpUtils";
    public static RequestBody buildReqeuestByHashmapWithImage(HashMap<String,String> hashMap, List<String> images){
        MultipartBody.Builder body = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(Map.Entry<String,String> entry:hashMap.entrySet()){
                body.addFormDataPart(entry.getKey(),entry.getValue());
        }

        if(images!=null){
            for(String filepath:images){
//                File file = new File(filepath);
//                Log.d(TAG, "buildReqeuestByHashmapWithImage: file"+file.getAbsolutePath()+"|"+file.length());
//
//                try {
//
//
//
//                    body.addFormDataPart("image",compressFile.getPath(),
//                            RequestBody.create(MediaType.parse("application/octet-stream"),
//                                    compressFile));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }



            }

        }

        return body.build();
    }





    public static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        if(httpLoggingInterceptor==null){
            httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("OkHttp3","url = "+message);
                    //对密码进行过滤
                    if(message.contains("password=")){
                        String[] mes = message.split("&");
                        for(String tmp :mes){
                            if(tmp.contains("password=")){
                                message = message.replace(tmp,"password=******");
                            }
                        }
                    }
//                        ErrorLogUtils.setLog(message);
                }
            });
        }
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     *  可以考虑做成单例模式
     */
    public static OkHttpClient.Builder getBuilder(){
        //全信任证书模式，可能无法通过双向的证书验证
        TrustAllManager trustAllManager = new TrustAllManager();
        SSLSocketFactory sslSocketFactory=OkHttpUtils.createTrustAllSSLFactory(trustAllManager);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//设置连接超时间
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS); //写操作超时时间
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS); //读操作超时时间
        builder.retryOnConnectionFailure(true); //错误重连
        builder.sslSocketFactory(sslSocketFactory,trustAllManager); //QA测试的时候用这个，全信任模式
        builder.hostnameVerifier((hostname, session) -> true);
        builder.addInterceptor(new CommonRequestInterceptor()); //公共头部参数
        builder.addInterceptor(getHttpLoggingInterceptor()); //请求日志拦截
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        return builder;
    }
    //配置连接超时时间，缓存，拦截器
    public static SSLSocketFactory createTrustAllSSLFactory(TrustAllManager trustAllManager) {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{trustAllManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return ssfFactory;
    }






public static Certificate getCertificateForRawResource(int resourceId, Context context) {
    CertificateFactory cf = null;
    Certificate ca = null;
    Resources resources = context.getResources();
    InputStream caInput = resources.openRawResource(resourceId);

    try {
        cf = CertificateFactory.getInstance("X.509");
        ca = cf.generateCertificate(caInput);
    } catch (CertificateException e) {
    } finally {
        try {
            caInput.close();
        } catch (IOException e) {
        }
    }

    return ca;
}

    public static Certificate convertSSLCertificateToCertificate(SslCertificate sslCertificate) {
        CertificateFactory cf = null;
        Certificate certificate = null;
        Bundle bundle = sslCertificate.saveState(sslCertificate);
        byte[] bytes = bundle.getByteArray("x509-certificate");

        if (bytes != null) {
            try {
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(bytes));
                certificate = cert;
            } catch (CertificateException e) {
            }
        }

        return certificate;
    }
}

class TrustAllManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new java.security.cert.X509Certificate[0];
    }
}