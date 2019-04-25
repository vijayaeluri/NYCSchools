package com.nyc.schools.viewmodel;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.util.Log;

import com.nyc.schools.common.INavHelper;
import com.nyc.schools.model.DataRepository;
import com.nyc.schools.model.School;
import com.nyc.schools.model.SchoolResponse;
import com.nyc.schools.network.ApiCallback;
import com.nyc.schools.network.NetworkManager;
import com.nyc.schools.network.ResponseWrapper;
import com.nyc.schools.utils.CommonUtil;

import java.util.List;

import retrofit2.HttpException;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class SchoolViewModel extends BaseViewModel<INavHelper> implements ApiCallback, Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public SchoolResponse schoolResponse;

    public SchoolResponse getSchoolResponse() {
        return schoolResponse;
    }

    public void getSchools() {
        NetworkManager.getInstance().getSchools(this);
    }

    public List<School> getSchools(String type) {
        return DataRepository.getInstance().getSchools(type);
    }

    @Override
    public void onSuccess(Object t, boolean fromCache) {

        getNavHelper().hideLoading();

        ResponseWrapper responseWrapper = (ResponseWrapper) t;
        this.schoolResponse = (SchoolResponse) responseWrapper.getResponse();

        DataRepository.getInstance().setSchools(schoolResponse.getSchools());

        getNavHelper().updateUI();
    }

    @Override
    public void onFailure(Throwable throwable) {

        getNavHelper().hideLoading();

        boolean shown = false;
        try {
            if (throwable instanceof HttpException) {

                //Based on HTTP Error code, display specific error message to user
                //Temporarily, displaying generic network error message

                String errorJson = ((HttpException) throwable).response().errorBody().string();
                if (!CommonUtil.isEmptyString(errorJson)) {
                    getNavHelper().showAlert(errorJson);
                    shown = true;
                }
            }
        } catch (Exception ex) {
            Log.d(SchoolViewModel.class.getSimpleName(), ex.getMessage());
        } finally {
            if (!shown) {
                getNavHelper().showApiError();
            }
        }
    }

    @Override
    public void onSubscribe() {
        getNavHelper().showLoading();
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    void notifyChange() {
        callbacks.notifyCallbacks(this, 0, null);
    }

    void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }
}
