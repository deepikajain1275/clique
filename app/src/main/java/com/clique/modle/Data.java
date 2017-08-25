package com.clique.modle;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {
    @SerializedName("phoneNo")
    @Expose

    public String phoneNo;
    @SerializedName("phoneno")
    @Expose
    public String phoneno;

    @SerializedName("isGetSigupDisount")
    @Expose
    public int isGetSigupDisount;
    @SerializedName("isGetInviteFriendDisount")
    @Expose
    public int isGetInviteFriendDisount;

    @SerializedName("isinhostelist")
    @Expose
    public int isinhostelist;

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("Name")
    @Expose
    public String Name;
    @SerializedName("Hostelname")
    @Expose
    public String Hostelname;

    @SerializedName("hostalid")
    @Expose
    public int hostalid;

    @SerializedName("profilePic")
    @Expose
    public String profilePic;

    public int userid;

    public String unregisteraddress;

    public Data()
    {

    }


    protected Data(Parcel in) {
        phoneNo = in.readString();
        phoneno = in.readString();
        isGetSigupDisount = in.readInt();
        isGetInviteFriendDisount = in.readInt();
        isinhostelist = in.readInt();
        name = in.readString();
        Name = in.readString();
        Hostelname = in.readString();
        hostalid = in.readInt();
        profilePic = in.readString();
        userid = in.readInt();
        unregisteraddress = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(phoneNo);
        parcel.writeString(phoneno);
        parcel.writeInt(isGetSigupDisount);
        parcel.writeInt(isGetInviteFriendDisount);
        parcel.writeInt(isinhostelist);
        parcel.writeString(name);
        parcel.writeString(Name);
        parcel.writeString(Hostelname);
        parcel.writeInt(hostalid);
        parcel.writeString(profilePic);
        parcel.writeInt(userid);
        parcel.writeString(unregisteraddress);
    }
}