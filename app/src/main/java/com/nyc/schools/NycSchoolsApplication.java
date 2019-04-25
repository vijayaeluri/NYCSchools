package com.nyc.schools;

import android.support.multidex.MultiDexApplication;

import com.squareup.picasso.Picasso;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class NycSchoolsApplication extends MultiDexApplication {

    private static NycSchoolsApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Picasso.setSingletonInstance(new Picasso.Builder(this).build());
    }

    public static com.nyc.schools.NycSchoolsApplication getInstance() {
        return instance;
    }
}
