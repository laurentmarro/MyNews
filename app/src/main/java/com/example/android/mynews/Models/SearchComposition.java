package com.example.android.mynews.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchComposition {

    @SerializedName("docs")
    @Expose
    private List<Search> docs = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Search> getDocs() {
        return docs;
    }

    public void setDocs(List<Search> docs) {
        this.docs = docs;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
