package com.nyc.schools.network;

import com.nyc.schools.utils.Constants;
import com.nyc.schools.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class RequestControlInterceptor implements Interceptor {

    private boolean forceNetwork;
    private boolean forceCache;

    /**
     * Force a network request, bypassing the cache validity.
     */
    public void forceNetwork() {
        forceNetwork = true;
    }

    /**
     * Force a cache request. Response might be valid or not based
     * on whether a cache was previously created from a valid response.
     */
    public void forceCache() {
        forceCache = true;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (forceNetwork) {
            request = request.newBuilder()
                    .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .cacheControl(CacheControl.FORCE_NETWORK).build();
            forceNetwork = false;
        }
        else if (forceCache || !NetworkUtil.isConnected()) {
            request = request.newBuilder()
                    .header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .cacheControl(CacheControl.FORCE_CACHE).build();
            forceCache = false;
        }

        return chain.proceed(request);
    }

}
