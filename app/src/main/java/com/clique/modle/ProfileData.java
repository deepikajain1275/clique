package com.clique.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProfileData implements Parcelable {

@SerializedName("status")
@Expose
public String status;
@SerializedName("Message")
@Expose
public String message;
@SerializedName("data")
@Expose
public ArrayList<Data> data;


    protected ProfileData(Parcel in) {
        status = in.readString();
        message = in.readString();
        data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<ProfileData> CREATOR = new Creator<ProfileData>() {
        @Override
        public ProfileData createFromParcel(Parcel in) {
            return new ProfileData(in);
        }

        @Override
        public ProfileData[] newArray(int size) {
            return new ProfileData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(message);
        parcel.writeTypedList(data);
    }
}