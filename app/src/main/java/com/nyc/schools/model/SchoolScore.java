package com.nyc.schools.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.nyc.schools.utils.CommonUtil;

public class SchoolScore extends BaseObservable implements Parcelable {

    @SerializedName("dbn")
    private String dbn;

    @SerializedName("num_of_sat_test_takers")
    private String numOfSatTestTakers;

    @SerializedName("sat_critical_reading_avg_score")
    private String satCriticalReadingAvgScore;

    @SerializedName("sat_writing_avg_score")
    private String satWritingAvgScore;

    @SerializedName("sat_math_avg_score")
    private String satMathAvgScore;

    @SerializedName("school_name")
    private String schoolName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dbn);
        dest.writeString(this.numOfSatTestTakers);
        dest.writeString(this.satCriticalReadingAvgScore);
        dest.writeString(this.satWritingAvgScore);
        dest.writeString(this.satMathAvgScore);
        dest.writeString(this.schoolName);
    }

    public SchoolScore() {
        //no op
    }

    public String getDbn() {
        return dbn;
    }

    @Bindable
    public String getNumOfSatTestTakers() {
        return CommonUtil.getEmptyIfNull(numOfSatTestTakers);
    }

    @Bindable
    public String getSatCriticalReadingAvgScore() {
        return CommonUtil.getEmptyIfNull(satCriticalReadingAvgScore);
    }

    @Bindable
    public String getSatWritingAvgScore() {
        return CommonUtil.getEmptyIfNull(satWritingAvgScore);
    }

    @Bindable
    public String getSatMathAvgScore() {
        return CommonUtil.getEmptyIfNull(satMathAvgScore);
    }

    @Bindable
    public String getSchoolName() {
        return CommonUtil.getEmptyIfNull(schoolName).trim();
    }

    protected SchoolScore(Parcel in) {
        this.dbn = in.readString();
        this.numOfSatTestTakers = in.readString();
        this.satCriticalReadingAvgScore = in.readString();
        this.satMathAvgScore = in.readString();
        this.schoolName = in.readString();
    }

    public static final Creator<SchoolScore> CREATOR = new Creator<SchoolScore>() {
        @Override
        public SchoolScore createFromParcel(Parcel source) {
            return new SchoolScore(source);
        }

        @Override
        public SchoolScore[] newArray(int size) {
            return new SchoolScore[size];
        }
    };
}
