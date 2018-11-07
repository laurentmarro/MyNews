package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Legacy {
    @SerializedName("xlarge")
    @Expose
    private String xlarge;
    @SerializedName("xlargewidth")
    @Expose
    private Integer xlargewidth;
    @SerializedName("xlargeheight")
    @Expose
    private Integer xlargeheight;
    @SerializedName("wide")
    @Expose
    private String wide;
    @SerializedName("widewidth")
    @Expose
    private Integer widewidth;
    @SerializedName("wideheight")
    @Expose
    private Integer wideheight;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("thumbnailwidth")
    @Expose
    private Integer thumbnailwidth;
    @SerializedName("thumbnailheight")
    @Expose
    private Integer thumbnailheight;
}
