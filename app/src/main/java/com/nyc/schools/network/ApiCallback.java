package com.nyc.schools.network;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public interface ApiCallback<T> {

    void onSubscribe();

    void onSuccess(T t, boolean fromCache);

    void onFailure(Throwable e);
}
