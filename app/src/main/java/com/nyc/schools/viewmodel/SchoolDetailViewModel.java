package com.nyc.schools.viewmodel;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.util.Log;

import com.nyc.schools.common.INavHelper;
import com.nyc.schools.model.DataRepository;
import com.nyc.schools.model.School;
import com.nyc.schools.model.SchoolScore;
import com.nyc.schools.model.SchoolScoreResponse;
import com.nyc.schools.network.ApiCallback;
import com.nyc.schools.network.NetworkManager;
import com.nyc.schools.network.ResponseWrapper;
import com.nyc.schools.utils.CommonUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.List;

import retrofit2.HttpException;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class SchoolDetailViewModel extends BaseViewModel<INavHelper> implements ApiCallback, Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();

    public SchoolScore schoolScore;
    public SchoolScoreResponse schoolScoreResponse;

    public SchoolScoreResponse getSchoolScoreResponse() {
        return schoolScoreResponse;
    }

    public void getSchoolScores() {
        NetworkManager.getInstance().getSchoolsScores(this);
    }

    public List<SchoolScore> getSchoolScoreList() {
        return DataRepository.getInstance().getSchoolScores();
    }

    public SchoolScore getSchoolScoreObject(final String dbn) {
        schoolScore = CollectionUtils.find(getSchoolScoreList(),
                new Predicate<SchoolScore>() {
                    public boolean evaluate(SchoolScore score) {
                        return dbn.equalsIgnoreCase(score.getDbn());
                    }
                });
        return schoolScore;
    }

    public void setSchoolToRepo(int index, String type, final boolean isFavorite) {
        List<School> list = DataRepository.getInstance().getSchools(type);
        if (list != null && !list.isEmpty() && index >= 0 && index < list.size()) {
            DataRepository.getInstance().getSchools(type).get(index).setFavorite(isFavorite);
        }
    }

    @Override
    public void onSuccess(Object t, boolean fromCache) {

        getNavHelper().hideLoading();

        ResponseWrapper responseWrapper = (ResponseWrapper) t;
        this.schoolScoreResponse = (SchoolScoreResponse) responseWrapper.getResponse();

        DataRepository.getInstance().setSchoolScores(schoolScoreResponse.getSchoolScores());

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
            Log.d(SchoolDetailViewModel.class.getSimpleName(), ex.getMessage());
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

    public void repoUpdated() {
        DataRepository.RepoUpdated object = new DataRepository.RepoUpdated();
        object.updated = true;
        DataRepository.getInstance().getEventBus().post(object);
    }
}
