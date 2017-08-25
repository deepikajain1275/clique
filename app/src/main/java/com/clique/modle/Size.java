package com.clique.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Size {
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("product")
    @Expose
    public List<Product> products = null;

}