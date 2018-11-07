package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("hits")
    @Expose
    private Integer hits;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("time")
    @Expose
    private Integer time;
}
