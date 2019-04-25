package com.nyc.schools.network;

import android.content.Context;

import com.nyc.schools.NycSchoolsApplication;
import com.nyc.schools.utils.Constants;
import com.nyc.schools.utils.NetworkUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class ApiServiceFactory {

    private static ApiServiceFactory instance;

    private ApiServiceFactory() {
    }

    public static ApiServiceFactory getInstance() {
        if (instance == null) {
            instance = new ApiServiceFactory();
        }
        return instance;
    }

    public <T> T getApiServiceInstance(final Class<T> tclass) {

        RequestControlInterceptor requestControlInterceptor = new RequestControlInterceptor();

        final Context context = NycSchoolsApplication.getInstance();
        final Cache cache = new Cache(new File(context.getCacheDir(), Constants.CACHE_DIR), Constants.CACHE_SIZE);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constants.TIMEOUT_CONNECTION_SECONDS, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT_READ_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT_WRITE_SECONDS, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(requestControlInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtil.getServiceBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(tclass);
    }
}
