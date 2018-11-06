package com.example.android.mynews.Models.MostPopularModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ArticleMostPopular {
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("count_type")
    @Expose
    private String countType;
    @SerializedName("column")
    @Expose
    private Object column;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("byline")
    @Expose
    private String byline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("abstract")
    @Expose
    private String _abstract;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private transient String source;
    @SerializedName("des_facet")
    @Expose
    private transient List<String> desFacet = null;
    @SerializedName("org_facet")
    @Expose
    private transient String orgFacet;
    @SerializedName("per_facet")
    @Expose
    private transient List<String> perFacet = null;
    @SerializedName("geo_facet")
    @Expose
    private transient String geoFacet;
    @SerializedName("media")
    @Expose
    private List<MediumMostPopular> media = null;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public Object getColumn() {
        return column;
    }

    public void setColumn(Object column) {
        this.column = column;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstract() {
        return _abstract;
    }

    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getDesFacet() {
        return desFacet;
    }

    public void setDesFacet(List<String> desFacet) {
        this.desFacet = desFacet;
    }

    public String getOrgFacet() {
        return orgFacet;
    }

    public void setOrgFacet(String orgFacet) {
        this.orgFacet = orgFacet;
    }

    public List<String> getPerFacet() {
        return perFacet;
    }

    public void setPerFacet(List<String> perFacet) {
        this.perFacet = perFacet;
    }

    public String getGeoFacet() {
        return geoFacet;
    }

    public void setGeoFacet(String geoFacet) {
        this.geoFacet = geoFacet;
    }

    public List<MediumMostPopular> getMedia() {
        return media;
    }

    public void setMedia(List<MediumMostPopular> media) {
        this.media = media;
    }

}
