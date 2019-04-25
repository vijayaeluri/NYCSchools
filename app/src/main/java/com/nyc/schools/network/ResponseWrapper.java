package com.nyc.schools.network;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class ResponseWrapper<T> {

    private final T response;

    public ResponseWrapper(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    public Class<T> getResponseClassType() {
        return (Class<T>) response.getClass();
    }
}
