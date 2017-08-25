package com.clique.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pincode implements Parcelable {

@SerializedName("status")
@Expose
public String status;
@SerializedName("Message")
@Expose
public String message;
@SerializedName("Data")
@Expose
public Data data;


    protected Pincode(Parcel in) {
        status = in.readString();
        message = in.readString();
    }

    public static final Creator<Pincode> CREATOR = new Creator<Pincode>() {
        @Override
        public Pincode createFromParcel(Parcel in) {
            return new Pincode(in);
        }

        @Override
        public Pincode[] newArray(int size) {
            return new Pincode[size];
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
    }
}