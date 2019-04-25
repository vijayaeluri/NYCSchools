package com.nyc.schools.common;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public interface INavHelper {

    void showLoading();

    void hideLoading();

    void showAlert(String message);

    void showNetworkError();

    void showApiError();

    void updateUI();
}
