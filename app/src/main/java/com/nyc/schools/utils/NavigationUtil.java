package com.nyc.schools.utils;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.nyc.schools.R;
import com.nyc.schools.model.School;
import com.nyc.schools.schools.DetailFragment;
import com.nyc.schools.schools.ListFragment;
import com.nyc.schools.utils.Constants.ActivityTag;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class NavigationUtil {

    @Constants.ActivityTag
    private static String activityTag = ActivityTag.NONE;

    @Constants.ActivityTag
    public static String getActivityTag() {
        return activityTag;
    }

    public static void setActivityTag(@Constants.ActivityTag String tag) {
        activityTag = tag;
    }

    public static void loadListFragment(FragmentManager mgr, String type) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_TYPE, type);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);

        mgr.beginTransaction()
                .replace(R.id.frame, fragment, ActivityTag.SCHOOL_LIST)
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .commitAllowingStateLoss();

        setActivityTag(ActivityTag.SCHOOL_LIST);
    }

    public static void loadDetailsFragment(FragmentManager mgr, int index, String type, School school) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_INDEX, index);
        bundle.putString(Constants.EXTRA_TYPE, type);
        bundle.putParcelable(Constants.EXTRA_SCHOOL, school);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);

        mgr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        mgr.beginTransaction()
                .replace(R.id.frame, fragment)
                .addToBackStack("detail")
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
                .commit();

        setActivityTag(ActivityTag.SCHOOL_DETAIL);
    }
}
