package com.nyc.schools.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.nyc.schools.utils.CommonUtil;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class School extends BaseObservable implements Parcelable, Comparable<School> {

    @SerializedName("dbn")
    private String dbn;

    @SerializedName("school_name")
    private String schoolNameString;

    @SerializedName("total_students")
    private String totalStudents;

    @SerializedName("neighborhood")
    private String neighborhood;

    @SerializedName("location")
    private String location;

    @SerializedName("state_code")
    private String stateCode;

    @SerializedName("zip")
    private String zip;

    @SerializedName("interest1")
    private String interest1;

    @SerializedName("method1")
    private String method1;

    @SerializedName("nta")
    private String nta;

    @SerializedName("offer_rate1")
    private String offerRate1;

    @SerializedName("overview_paragraph")
    private String overviewParagraph;

    @SerializedName("website")
    private String website;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("borough")
    private String borough;

    @SerializedName("grades2018")
    private String grades2018;

    @SerializedName("extracurricular_activities")
    private String extracurricularActivities;

    @SerializedName("school_sports")
    private String schoolSports;

    @SerializedName("graduation_rate")
    private String graduationRate;


    private SchoolScore schoolScore;
    private boolean isFavorite = false;

    @Bindable
    public String getSchoolName() {
        return CommonUtil.getEmptyIfNull(schoolNameString).trim();
    }

    @Bindable
    public String getSchoolAddress() {
        return CommonUtil.getEmptyIfNull(neighborhood) + ", \n"
                + CommonUtil.getEmptyIfNull(location).substring(0, CommonUtil.getEmptyIfNull(location).indexOf("("));
    }

    @Bindable
    public String getTotalStudents() {
        return CommonUtil.getEmptyIfNull(totalStudents);
    }

    @Bindable
    public String getGrades2018() {
        return CommonUtil.getEmptyIfNull(grades2018);
    }

    @Bindable
    public String getOverviewParagraph() {
        return CommonUtil.getEmptyIfNull(overviewParagraph);
    }

    @Bindable
    public String getExtracurricularActivities() {
        return CommonUtil.getEmptyIfNull(extracurricularActivities);
    }

    public String getDbn() {
        return dbn;
    }

    @Bindable
    public String getPhoneNumber() {
        return CommonUtil.getEmptyIfNull(phoneNumber);
    }

    @Bindable
    public String getWebsite() {
        return CommonUtil.getEmptyIfNull(website);
    }

    @Bindable
    public String getSchoolSports() {
        return CommonUtil.getEmptyIfNull(schoolSports);
    }

    @Bindable
    public String getInterest1() {
        return CommonUtil.getEmptyIfNull(interest1);
    }

    @Bindable
    public String getGraduationRate() {
        return CommonUtil.getEmptyIfNull(graduationRate);
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schoolNameString);
        dest.writeString(this.totalStudents);
        dest.writeString(this.stateCode);
        dest.writeString(this.zip);
        dest.writeByte(this.isFavorite ? (byte) 1 : (byte) 0);
    }

    public School() {
    }

    protected School(Parcel in) {
        this.schoolNameString = in.readString();
        this.totalStudents = in.readString();
        this.stateCode = in.readString();
        this.zip = in.readString();
        this.isFavorite = in.readByte() != 0;
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel source) {
            return new School(source);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };

    @Override
    public int compareTo(@NonNull School school) {
        return this.getSchoolName().compareToIgnoreCase(school.getSchoolName());
    }
}
