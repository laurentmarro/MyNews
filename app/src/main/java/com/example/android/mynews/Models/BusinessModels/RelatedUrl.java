package com.example.android.mynews.Models.BusinessModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedUrl {

    @SerializedName("suggested_link_text")
    @Expose
    private transient String suggestedLinkText;
    @SerializedName("url")
    @Expose
    private transient String url;

    public String getSuggestedLinkText() {
        return suggestedLinkText;
    }

    public void setSuggestedLinkText(String suggestedLinkText) {
        this.suggestedLinkText = suggestedLinkText;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}