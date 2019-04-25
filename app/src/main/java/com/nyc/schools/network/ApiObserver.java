package com.nyc.schools.network;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class ApiObserver<T> implements SingleObserver<T> {

    private ApiCallback apiCallback;

    public ApiObserver(ApiCallback callback) {
        this.apiCallback = callback;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        if (apiCallback != null) {
            apiCallback.onSubscribe();
        }
    }

    @Override
    public void onSuccess(T response) {
        if (apiCallback != null) {
            ResponseWrapper<T> responseWrapper = new ResponseWrapper<>(response);
            apiCallback.onSuccess(responseWrapper, false);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (apiCallback != null) {
            apiCallback.onFailure(e);
        }
    }
}
