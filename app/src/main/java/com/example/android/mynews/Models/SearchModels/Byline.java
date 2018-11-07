package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Byline {
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("person")
    @Expose
    private List<Person> person = null;
    @SerializedName("organization")
    @Expose
    private Object organization;
}