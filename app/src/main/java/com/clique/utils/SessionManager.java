package com.clique.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.HashMap;

import com.clique.modle.Data;
import com.clique.modle.FinalProduct;

/**
 * Created by Admin on 03-07-2015.
 */
public class SessionManager {
    public static final String PREF_SELECT_DATA = "selectdata";
    public static final String PREF_USER_DATA = "profileinfo";
    private static final String PREF_TUT = "tut";
    public static final String PREF_NAME_DEVICE_TOKEN = "devicetoken";
    public static final String PREF_SELECT_DATA1 = "selectdat2";
    SharedPreferences pref, postpref, preftut, preft_Device, pref_data1;
    SharedPreferences.Editor editor, editor_tut, editor_device_token, editor_post;
    Context _context;
    int PRIVATE_MODE = 0;
    private SharedPreferences.Editor editor_post1;

    public SessionManager(Context context) {
        this._context = context;
        postpref = _context.getSharedPreferences(PREF_SELECT_DATA, PRIVATE_MODE);
        pref = _context.getSharedPreferences(PREF_USER_DATA, PRIVATE_MODE);
        preftut = _context.getSharedPreferences(PREF_TUT, PRIVATE_MODE);
        preft_Device = _context.getSharedPreferences(PREF_NAME_DEVICE_TOKEN, PRIVATE_MODE);
        pref_data1 = _context.getSharedPreferences(PREF_SELECT_DATA1, PRIVATE_MODE);
        editor = pref.edit();
        editor_post = postpref.edit();
        editor_tut = preftut.edit();
        editor_device_token = preft_Device.edit();
        editor_post1 = pref_data1.edit();
    }

    public static SharedPreferences getSharedPreferences(Context context, String prefname) {
        return context.getSharedPreferences(prefname, Context.MODE_PRIVATE);
    }

    public void putDeviceToken(String deviceid) {
        if (!deviceid.equals(""))
            editor_device_token.putString(Constant.DEVICEID, deviceid);
        editor_device_token.commit();
    }

    public HashMap<String, String> get(Context context) {
        HashMap<String, String> map = new HashMap<>();
        SharedPreferences prf = context.getSharedPreferences(PREF_SELECT_DATA, Context.MODE_PRIVATE);
        if (prf.contains(Constant.NAME)) {
            map.put(Constant.NAME, prf.getString(Constant.NAME, ""));
            map.put(Constant.ID, prf.getString(Constant.ID, ""));
            map.put(Constant.PRICE, prf.getString(Constant.PRICE, ""));
            map.put(Constant.SIZE, prf.getString(Constant.SIZE, ""));
            map.put(Constant.IMAGE, prf.getString(Constant.IMAGE, ""));
            return map;
        } else
            return null;
    }

    public void putArrayListOrder(FinalProduct finalProduct) {
        editor_post.putString(Constant.ORDER, getJsonString(finalProduct));
        editor_post.commit();
    }
    public FinalProduct getArrayLisOrder(Context context)
    {
        SharedPreferences prf = context.getSharedPreferences(PREF_SELECT_DATA, Context.MODE_PRIVATE);
        String oderlist=prf.getString(Constant.ORDER,"");
       return (FinalProduct) parseFromString(oderlist,FinalProduct.class);
    }

    public void putData(String image, String name, String id, String price, String size) {
        editor_post.putString(Constant.NAME, name);
        editor_post.putString(Constant.ID, id);
        editor_post.putString(Constant.PRICE, price);
        editor_post.putString(Constant.SIZE, size);
        editor_post.putString(Constant.IMAGE, image);
        editor_post.commit();
    }

    public HashMap<String, String> getData2(Context context) {
        HashMap<String, String> map = new HashMap<>();
        SharedPreferences prf = context.getSharedPreferences(PREF_SELECT_DATA1, Context.MODE_PRIVATE);
        if (prf.contains(Constant.NAME)) {
            map.put(Constant.NAME, prf.getString(Constant.NAME, ""));
            map.put(Constant.ID, prf.getString(Constant.ID, ""));
            map.put(Constant.PRICE, prf.getString(Constant.PRICE, ""));
            map.put(Constant.SIZE, prf.getString(Constant.SIZE, ""));
            map.put(Constant.IMAGE, prf.getString(Constant.IMAGE, ""));
            return map;
        }
        return null;
    }

    public void putData1(String image, String name, String id, String price, String size) {
        editor_post1.putString(Constant.NAME, name);
        editor_post1.putString(Constant.ID, id);
        editor_post1.putString(Constant.PRICE, price);
        editor_post1.putString(Constant.SIZE, size);
        editor_post1.putString(Constant.IMAGE, image);
        editor_post1.commit();
    }

    public String getDeviceToken(Context context) {
        return context.getSharedPreferences(PREF_NAME_DEVICE_TOKEN, Context.MODE_PRIVATE).getString(Constant.DEVICEID, "");
    }

    public void putSharedPreferenece(String name, int userid,String hostalname,String phoneNo,String hostalid,
                                     String profilePic,int isGetSigupDisount,int isGetInviteFriendDisount,int isinhostelist) {
            editor.putString(Constant.NAME, name);
            editor.putInt(Constant.USERID, userid);
            editor.putString(Constant.HOSTALNAME, hostalname);
            editor.putString(Constant.PHONENO, phoneNo);
            editor.putString(Constant.HOSTALID, hostalid+"");
            editor.putString(Constant.PROFILEPIC, profilePic);
            editor.putInt(Constant.ISSIGNUPDISCOUNT, isGetSigupDisount);
            editor.putInt(Constant.ISINVITEDISOUNT, isGetInviteFriendDisount);
            editor.putInt(Constant.ISINHOSTALLIST, isinhostelist);
            editor.commit();
    }

    public static Data getSharedPreferenece(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER_DATA, Context.MODE_PRIVATE);
        Data data = new Data();
        if (sharedPreferences.contains(Constant.PHONENO)) {
            data.userid = sharedPreferences.getInt(Constant.USERID, 0);
            data.name = sharedPreferences.getString(Constant.NAME, "");
            data.isGetSigupDisount = sharedPreferences.getInt(Constant.ISSIGNUPDISCOUNT, 0);
            data.Hostelname = sharedPreferences.getString(Constant.HOSTALNAME, "");
            data.phoneNo = sharedPreferences.getString(Constant.PHONENO, "");
            data.hostalid = Integer.parseInt(sharedPreferences.getString(Constant.HOSTALID, ""));
            data.profilePic = sharedPreferences.getString(Constant.PROFILEPIC, "");
            data.isinhostelist = sharedPreferences.getInt(Constant.ISINHOSTALLIST, 0);
            data.isGetInviteFriendDisount = sharedPreferences.getInt(Constant.ISINVITEDISOUNT, 0);
            return data;
        } else return null;
    }

    public void putTut(int show) {
        editor_tut.putInt(Constant.TUTSHOW, show);
        editor_tut.commit();
    }

    public static int getTut(Context context) {
        return context.getSharedPreferences(PREF_TUT, Context.MODE_PRIVATE).getInt(Constant.TUTSHOW, 0);
    }

    public void clearData() {
        editor.clear();
        editor_tut.clear();
        editor_tut.commit();
        editor.commit();
    }

    public static Object parseFromString(String jsonData, Class modelClass) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(jsonData));
        reader.setLenient(true);
        return new Gson().fromJson(reader, modelClass);
    }

    public static String getJsonString(Object modelClass) {
        return new Gson().toJson(modelClass);
    }
}
