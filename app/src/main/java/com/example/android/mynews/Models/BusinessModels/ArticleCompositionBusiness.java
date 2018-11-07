package com.example.android.mynews.Models.BusinessModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArticleCompositionBusiness {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
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

    public ArticleCompositionBusiness withResults(List<ArticleBusiness> results) {
        this.results = results;
        return this;
    }
}
