package com.nyc.schools.utils;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class Constants {

    public static int TIMEOUT_CONNECTION_SECONDS = 15;
    public static int TIMEOUT_READ_SECONDS = 15;
    public static int TIMEOUT_WRITE_SECONDS = 15;

    public static final String CACHE_DIR = "http-cache";
    public static final int CACHE_SIZE = 10 * 1024 * 1024; //10MB
    public static final int CACHE_DURATION = (int) TimeUnit.MINUTES.toSeconds(15);

    public static String CONTENT_TYPE = "Content-Type";
    public static String APPLICATION_JSON = "application/json";

    public static final  int VIEW_TYPE_LIST = 0;
    public static final String EXTRA_INDEX = "EXTRA_INDEX";
    public static final String EXTRA_TYPE = "EXTRA_TYPE";
    public static final String EXTRA_SCHOOL = "EXTRA_SCHOOL";

    @StringDef({
            ActivityTag.SCHOOL_LIST,
            ActivityTag.SCHOOL_DETAIL,
            ActivityTag.NONE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityTag {

        String SCHOOL_LIST = "SCHOOL_LIST";
        String SCHOOL_DETAIL = "SCHOOL_DETAIL";
        String NONE = "NONE";
    }
}
