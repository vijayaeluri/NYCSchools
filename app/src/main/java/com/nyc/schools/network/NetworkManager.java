package com.nyc.schools.network;

import com.nyc.schools.model.School;
import com.nyc.schools.model.SchoolResponse;
import com.nyc.schools.model.SchoolScore;
import com.nyc.schools.model.SchoolScoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class NetworkManager {

    private static NetworkManager instance;
    private ApiService apiService;

    private NetworkManager() {
        apiService = ApiServiceFactory.getInstance().getApiServiceInstance(ApiService.class);
    }

    public static synchronized NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public void getSchools(final ApiCallback apiCallback) {

        apiService.getSchools().enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                SchoolResponse schoolResponse = new SchoolResponse();
                schoolResponse.setSchools(response.body());

                ResponseWrapper responseWrapper = new ResponseWrapper(schoolResponse);
                if (apiCallback != null) {
                    apiCallback.onSuccess(responseWrapper, false);
                }
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable throwable) {
                if (apiCallback != null) {
                    apiCallback.onFailure(throwable);
                }
            }
        });
    }

    public void getSchoolsScores(final ApiCallback apiCallback) {

        apiService.getSchoolsScores().enqueue(new Callback<List<SchoolScore>>() {
            @Override
            public void onResponse(Call<List<SchoolScore>> call, Response<List<SchoolScore>> response) {
                SchoolScoreResponse schoolScoreResponse = new SchoolScoreResponse();
                schoolScoreResponse.setSchoolScores(response.body());

                ResponseWrapper responseWrapper = new ResponseWrapper(schoolScoreResponse);
                if (apiCallback != null) {
                    apiCallback.onSuccess(responseWrapper, false);
                }
            }

            @Override
            public void onFailure(Call<List<SchoolScore>> call, Throwable throwable) {
                if (apiCallback != null) {
                    apiCallback.onFailure(throwable);
                }
            }
        });
    }
}
