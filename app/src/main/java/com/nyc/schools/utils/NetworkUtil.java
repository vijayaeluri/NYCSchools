package com.nyc.schools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.nyc.schools.BuildConfig;
import com.nyc.schools.NycSchoolsApplication;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class NetworkUtil {

    /**
     * Checks if the device has a network connection.
     */
    public static boolean isConnected() {
        Context context = NycSchoolsApplication.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String getServiceBaseUrl() {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            return BuildConfig.PROD_BASE_URL;
        } else if (BuildConfig.BUILD_TYPE.equals("stage")) {
            return BuildConfig.TEST_BASE_URL;
        } else if (BuildConfig.BUILD_TYPE.equals("debug")) {
            return BuildConfig.DEV_BASE_URL;
        } else {
            return BuildConfig.DEV_BASE_URL;
        }
    }
}
