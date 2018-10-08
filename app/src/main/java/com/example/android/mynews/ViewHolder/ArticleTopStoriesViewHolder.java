package com.example.android.mynews.ViewHolder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.example.android.mynews.Models.ArticleTopStories;
import com.example.android.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleTopStoriesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.sectionandsubsection) TextView sectionandsubsection;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.articleimage) ImageView articleimage;
    @BindView(R.id.date) TextView article_date;

    public ArticleTopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticles(ArticleTopStories article, RequestManager glide) {

        String sectionAndSubsection;
        String formattedDate;
        String description_text;
        String url_image;

        // Section and SubSection
        if (article.getSection().equals("")) {
            sectionAndSubsection = "";
        } else if (article.getSubsection().equals("")) {
            sectionAndSubsection = article.getSection();
        } else sectionAndSubsection = article.getSection() + " > " + article.getSubsection();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-mm-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat DesiredFormat = new SimpleDateFormat("dd/mm/yyyy");

        // Date
        try {
            formattedDate = DesiredFormat.format(sourceFormat.parse(article.getPublishedDate().substring(0, 10)));
        } catch (ParseException e) {
            formattedDate = "Unknown";
        }

        // Description
        if (article.getAbstract().equals("")){
            description_text = "Unwritten";
        } else description_text = article.getAbstract();

        // Image
        try {
            url_image = article.getMultimedia().get(0).getUrl();
        } catch (Exception e) {
            url_image = "https://upload.wikimedia.org/wikipedia/en/d/d3/No-picture.jpg";
        }

        // Display
        this.article_date.setText(formattedDate);
        this.sectionandsubsection.setText(sectionAndSubsection);
        this.description.setText(description_text);
        glide.load(url_image).into(articleimage);
    }
}