package com.mt.templatepro.api.loader;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseLoader {
    protected <T> Observable<T> observe(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())//切换到子线程
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());//切换到主线程
    }
}
