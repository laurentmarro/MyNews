package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Response {
    @SerializedName("docs")
    @Expose
    private List<ArticleSearch> docs = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<ArticleSearch> getDocs() {
        return docs;
    }

    public void setDocs(List<ArticleSearch> docs) {
        this.docs = docs;
    }
}