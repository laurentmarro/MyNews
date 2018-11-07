package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headline {
    @SerializedName("main")
    @Expose
    private String main;
    @SerializedName("kicker")
    @Expose
    private Object kicker;
    @SerializedName("content_kicker")
    @Expose
    private Object contentKicker;
    @SerializedName("print_headline")
    @Expose
    private String printHeadline;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("seo")
    @Expose
    private Object seo;
    @SerializedName("sub")
    @Expose
    private Object sub;
}
