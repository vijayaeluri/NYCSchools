package com.nyc.schools.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class SchoolResponse extends BaseObservable implements Parcelable {

    public SchoolResponse() {
        //no op
    }

    @Bindable
    private List<School> schools;

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.schools);
    }

    protected SchoolResponse(Parcel in) {
        this.schools = in.createTypedArrayList(School.CREATOR);
    }

    public static final Creator<SchoolResponse> CREATOR = new Creator<SchoolResponse>() {
        @Override
        public SchoolResponse createFromParcel(Parcel source) {
            return new SchoolResponse(source);
        }

        @Override
        public SchoolResponse[] newArray(int size) {
            return new SchoolResponse[size];
        }
    };
}
