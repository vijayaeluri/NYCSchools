package com.nyc.schools.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class SchoolScoreResponse extends BaseObservable implements Parcelable {

    public SchoolScoreResponse() {
        //no op
    }

    @Bindable
    private List<SchoolScore> schoolScores;

    public List<SchoolScore> getSchoolScores() {
        return this.schoolScores;
    }

    public void setSchoolScores(List<SchoolScore> schoolScores) {
        this.schoolScores = schoolScores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.schoolScores);
    }

    protected SchoolScoreResponse(Parcel in) {
        this.schoolScores = in.createTypedArrayList(SchoolScore.CREATOR);
    }

    public static final Creator<SchoolScoreResponse> CREATOR = new Creator<SchoolScoreResponse>() {
        @Override
        public SchoolScoreResponse createFromParcel(Parcel source) {
            return new SchoolScoreResponse(source);
        }

        @Override
        public SchoolScoreResponse[] newArray(int size) {
            return new SchoolScoreResponse[size];
        }
    };
}
