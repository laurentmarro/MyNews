package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multimedium {

    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("credit")
    @Expose
    private Object credit;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("subType")
    @Expose
    private String subType;
    @SerializedName("crop_name")
    @Expose
    private String cropName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
