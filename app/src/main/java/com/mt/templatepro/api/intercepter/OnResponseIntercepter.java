package com.mt.templatepro.api.intercepter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.mt.templatepro.api.exception.UnknownAPIVersionException;
import com.mt.templatepro.api.exception.VersionTooOldException;
import com.mt.templatepro.utils.AlertUtils;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import androidx.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

//统一的response预处理
public class OnResponseIntercepter extends DisposableObserver<ResponseBody> {
    private static final String TAG ="OnResponseIntercepter";
    private ResponsePretreatListener responsePretreatListener; //请求处理的监听器
    private Context context;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if(context!=null){
                Log.i(TAG,"请求api完毕");
                if(context instanceof Activity){
                    if(!((Activity)context).isFinishing())
                        AlertUtils.loadDissmiss();
                }else{
                    AlertUtils.loadDissmiss();
                }
            }
            return true;
        }
    });
    @Override
    public void onNext(ResponseBody responseBody) {

        Log.i(TAG,"onNext");
        //成功接收到有效的数据,并返回给调用者
        if(context!=null){
            Log.i(TAG,"请求api完毕");
            if(context instanceof Activity){
                if(!((Activity)context).isFinishing())
                    AlertUtils.loadDissmiss();
            }else{
                AlertUtils.loadDissmiss();
            }
        }
        String result = "";
        try {
            result = responseBody.string();
            responsePretreatListener.onSuccess(result);
        } catch (Exception e) {
//            ErrorLogUtils.getprintStackInfo(e);
            e.printStackTrace();
        }

    }

    public OnResponseIntercepter(ResponsePretreatListener responseToast, Context context) {
        this.responsePretreatListener = responseToast;
        this.context = context;
    }

    /**
     * 对错误进行统一处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if(context!=null){
            Log.i(TAG,"请求api完毕");
            if(context instanceof Activity){
                if(!((Activity)context).isFinishing())
                    AlertUtils.loadDissmiss();
            }else{
                AlertUtils.loadDissmiss();
            }
            //todo
        }
        if(e instanceof SocketTimeoutException){
            Log.i(TAG,"SocketTimeoutException");
            responsePretreatListener.onFailed("服务器连接超时",400);//网络连接超时
//            ErrorLogUtils.setLog(e.toString());
            AlertUtils.toastAlert("服务器连接超时 : 400");
        }//请求超时
        else if (e instanceof ConnectException){
            Log.i(TAG,"网络连接超时");
            responsePretreatListener.onFailed("服务器连接超时",400);//网络连接超时
//            ErrorLogUtils.setLog(e.toString());
            AlertUtils.toastAlert("服务器连接超时 : 400");
        }
        else if(e instanceof SSLHandshakeException){
            Log.i(TAG,"SSL证书异常");
            responsePretreatListener.onFailed("SSL证书异常",403);
//            ErrorLogUtils.setLog(e.toString());
            AlertUtils.toastAlert("SSL证书异常: 400");
        }
        else if(e instanceof HttpException){
            //获取错误码
            int code = ((HttpException) e).code();
            String msg = null;
            try {
                //获取错误的response信息
                msg = ((HttpException)e).response().errorBody().string();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Log.i(TAG,"code="+code);
            if(code == 504||code==502){
                responsePretreatListener.onFailed(msg,code);
            }else if(code==400){
                AlertUtils.toastAlert(""+JSONObject.parseObject(msg).getString("msg"));
            }
            else if(code == 404){
                responsePretreatListener.onFailed(msg,code);
            }else if(code == 601 || code == 600) {
                //包括了token过期，sso未登录，
                responsePretreatListener.onFailed(msg,code);
            }
            else{
                responsePretreatListener.onFailed(msg,code);
            }
            String temp = "error : code ="+code+"; msg = "+msg;
//            ErrorLogUtils.setLog(temp);
        }
        else if(e instanceof UnknownHostException){
            Log.i(TAG,"域名解析失败");
            responsePretreatListener.onFailed("域名解析失败",400);
//            ErrorLogUtils.setLog(e.toString());

        }else if(e instanceof UnknownAPIVersionException){
            Log.i(TAG,"未知的API版本");
//            ErrorLogUtils.setLog(e.toString());
            AlertUtils.toastAlert("未知的API服务器");

        }else if(e instanceof VersionTooOldException){
            Log.i(TAG,"存在版本更新");
//            ErrorLogUtils.setLog(e.toString());
            // 发送APP更新消息
//            APPUpdateEvenBean appUpdateEvenBean = new APPUpdateEvenBean(ConstValue.EVENTBUS_TYPE.APPUPDATE,"存在版本更新");
//            EventBus.getDefault().post(appUpdateEvenBean);
        }else{
            //其他未知类型的错误
            Log.i(TAG,"other"+e.getMessage());
            responsePretreatListener.onFailed(e.getMessage(),400);
//            ErrorLogUtils.setLog(e.toString());
        }
    }

    //TODO 如果需要在请求结束的时候实现额外的操作，可以在该函数里面实现
    @Override
    public void onComplete() {
        //TODO 请求成功之后，需要隐藏加载框，在这里，自己实现

    }
    //TODO 如果需要在请求开始的时候实现额外的操作，可以在该函数里面实现
    @Override
    protected void onStart() {
        super.onStart();
        //TODO 需要显示加载框的，看这里，自己实现
        if(context==null)return;
        if(!((Activity)context).isFinishing())
        AlertUtils.getLoadingDialogInstance((Activity) context).show();
    }


    public interface  ResponsePretreatListener{
        void onFailed(String result, int code);
        void onSuccess(String result);
    }



}
