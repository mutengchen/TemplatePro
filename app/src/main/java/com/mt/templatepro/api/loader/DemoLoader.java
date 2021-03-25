package com.mt.templatepro.api.loader;

import com.mt.templatepro.api.imp.DemoApi;
import com.mt.templatepro.api.retrofit.RetrofitClient;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class DemoLoader extends BaseLoader {
    private DemoApi demoApi;
    private static DemoLoader demoLoader;
    public static DemoLoader getInstance(){
        if(demoLoader == null){
            demoLoader = new DemoLoader();
        }
        return demoLoader;
    }

    public DemoLoader() {
        this.demoApi = RetrofitClient.getInstance(DemoApi.class);
    }
    public io.reactivex.Observable<ResponseBody> getNotifyList(RequestBody body) {
        return observe(demoApi.getNotifyList(body));
    }
}
