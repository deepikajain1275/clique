package com.clique.modle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Deepika on 07-08-2017.
 */

public class HostalList {
    @SerializedName("hostalid")
    @Expose
    public  int hostalid;
    @SerializedName("hostalname")
    @Expose
    public String hostalname;
}
