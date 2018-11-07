package com.example.android.mynews.Models.SearchModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("middlename")
    @Expose
    private Object middlename;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("qualifier")
    @Expose
    private Object qualifier;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("organization")
    @Expose
    private String organization;
    @SerializedName("rank")
    @Expose
    private Integer rank;
}