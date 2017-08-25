package com.clique.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubBrand {

    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("SubBrandName")
    @Expose
    public String subBrandName;
    @SerializedName("Image")
    @Expose
    public String image;
    @SerializedName("size")
    @Expose
    public List<Size> size = null;


    public int FlowType=0;
}