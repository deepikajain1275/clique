package com.clique.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("ID")
    @Expose
    public String iD;
    @SerializedName("ProductName")
    @Expose
    public String productName;
    @SerializedName("Image")
    @Expose
    public String image;
    @SerializedName("IsWing")
    @Expose
    public String isWing;
    @SerializedName("Pads")
    @Expose
    public int pads=0;

}