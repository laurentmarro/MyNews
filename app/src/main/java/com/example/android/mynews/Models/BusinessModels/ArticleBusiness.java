package com.example.android.mynews.Models.BusinessModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArticleBusiness {
    @SerializedName("slug_name")
    @Expose
    private String slugName;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("subsection")
    @Expose
    private String subsection;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("thumbnail_standard")
    @Expose
    private String thumbnailStandard;
    @SerializedName("item_type")
    @Expose
    private String itemType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("first_published_date")
    @Expose
    private String firstPublishedDate;
    @SerializedName("material_type_facet")
    @Expose
    private String materialTypeFacet;
    @SerializedName("kicker")
    @Expose
    private Object kicker;
    @SerializedName("subheadline")
    @Expose
    private Object subheadline;
    @SerializedName("des_facet")
    @Expose
    private List<String> desFacet = null;
    @SerializedName("org_facet")
    @Expose
    private List<String> orgFacet = null;
    @SerializedName("per_facet")
    @Expose
    private List<String> perFacet = null;
    @SerializedName("geo_facet")
    @Expose
    private List<String> geoFacet = null;
    @SerializedName("related_urls")
    @Expose
    private Object relatedUrls;
    @SerializedName("multimedia")
    @Expose
    private List<MultimediumBusiness> multimedia = null;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public ArticleBusiness withSection(String section) {
        this.section = section;
        return this;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public ArticleBusiness withSubsection(String subsection) {
        this.subsection = subsection;
        return this;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public ArticleBusiness withAbstract(String _abstract) {
        this._abstract = _abstract;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArticleBusiness withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ArticleBusiness withPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
        return this;
    }

    public List<MultimediumBusiness> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediumBusiness> multimedia) {
        this.multimedia = multimedia;
    }

    public ArticleBusiness withMultimedia(List<MultimediumBusiness> multimedia) {
        this.multimedia = multimedia;
        return this;
    }
}
