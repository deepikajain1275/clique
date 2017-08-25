package com.clique.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {
    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("BrandName")
    @Expose
    public String brandName;
    @SerializedName("Image")
    @Expose
    public String image;
    @SerializedName("SubBrand")
    @Expose
    public List<SubBrand> subBrand = null;
}