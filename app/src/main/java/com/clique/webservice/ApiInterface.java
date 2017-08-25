package com.clique.webservice;

import com.clique.modle.AllBrandData;
import com.clique.modle.Pincode;
import com.clique.modle.ProfileData;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("create.php")
    Call<AllBrandData> getCreateModule();

    @GET("hostel-list.php")
    Call<AllBrandData> getHostalList();

    @GET("pincode-phone.php")
    Call<Pincode> addPincodePhoneno(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("phone_and_hosteladdress.php")
    Call<Pincode> phoneAndHostal(@Field("phone") String phone, @Field("hostalid") int hostalid);

    @FormUrlEncoded
    @POST("profile.php")
    Call<ProfileData> updateProfile(@Field("userID") String id, @Field("HostalID") int hostalid, @Field("Phoneno") String phoneno,@Field("Name")String name);

    @FormUrlEncoded
    @POST("phone_no.php")
    Call<Pincode> phoneNo( @Field("phone") String phoneno);

    @FormUrlEncoded
    @POST("Unregisteraddress.php")
    Call<Pincode> unregisteraddress(@Field("Unregisteraddress") String unregisteraddress, @Field("userid") int userid, @Field("product") ArrayList<String> product);

    @Multipart
    @POST("profile.php")
    Call<ProfileData> updateProflieModule(@Part("userID") String ID, @Part("Name") String Name, @Part("HostalID") String hostalid, @Part("Phoneno") String phoneno);//, @Part MultipartBody.Part profilepic
}