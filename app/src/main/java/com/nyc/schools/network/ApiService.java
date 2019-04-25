package com.nyc.schools.network;

import com.nyc.schools.model.School;
import com.nyc.schools.model.SchoolResponse;
import com.nyc.schools.model.SchoolScore;
import com.nyc.schools.model.SchoolScoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public interface ApiService {

    @GET("s3k6-pzi2.json")
    Call<List<School>> getSchools();

    @GET("f9bf-2cp4.json")
    Call<List<SchoolScore>> getSchoolsScores();
}
