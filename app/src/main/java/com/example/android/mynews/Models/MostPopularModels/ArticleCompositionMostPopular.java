package com.example.android.mynews.Models.MostPopularModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArticleCompositionMostPopular {

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
    private List<ArticleMostPopular> results = null;

    public List<ArticleMostPopular> getResults() {
        return results;
    }

    public void setResults(List<ArticleMostPopular> results) {
        this.results = results;
    }

}