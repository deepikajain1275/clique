package com.clique.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllBrandData {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<Brand> data = null;
    @SerializedName("Data")
    @Expose
    public List<HostalList> Data = null;
}