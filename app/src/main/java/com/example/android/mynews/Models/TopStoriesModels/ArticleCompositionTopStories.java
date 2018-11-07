package com.example.android.mynews.Models.TopStoriesModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArticleCompositionTopStories {

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
    private List<ArticleTopStories> results = null;

    public List<ArticleTopStories> getResults() {
        return results;
    }

    public void setResults(List<ArticleTopStories> results) {
        this.results = results;
    }
}
