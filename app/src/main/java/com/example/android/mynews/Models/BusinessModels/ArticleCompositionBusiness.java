package com.example.android.mynews.Models.BusinessModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleCompositionBusiness {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("last_updated")
    @Expose
    private String lastUpdated;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;
    @SerializedName("results")
    @Expose
    private List<ArticleBusiness> results = null;

    public List<ArticleBusiness> getResults() {
        return results;
    }

    public void setResults(List<ArticleBusiness> results) {
        this.results = results;
    }

}
