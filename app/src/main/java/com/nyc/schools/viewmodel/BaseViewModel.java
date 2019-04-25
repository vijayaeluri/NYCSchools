package com.nyc.schools.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class BaseViewModel<N> extends ViewModel {

    private Context context;
    private N navHelper;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public N getNavHelper() {
        return navHelper;
    }

    public void setNavHelper(N navHelper) {
        this.navHelper = navHelper;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
