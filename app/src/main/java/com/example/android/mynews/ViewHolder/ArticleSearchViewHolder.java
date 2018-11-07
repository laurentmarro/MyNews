package com.example.android.mynews.ViewHolder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import com.example.android.mynews.Models.SearchModels.ArticleSearch;
import com.example.android.mynews.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleSearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.sectionandsubsection) TextView sectionandsubsection;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.articleimage) ImageView articleimage;
    @BindView(R.id.date) TextView article_date;

    public ArticleSearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithArticles(ArticleSearch article, RequestManager glide) {

        String sectionAndSubsection;
        String formattedDate;
        String description_text;
        String url_image;

        // Section and SubSection
        if (article.getSectionName().equals("")) {
            sectionAndSubsection = "";
        } else sectionAndSubsection = article.getSectionName();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-mm-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat DesiredFormat = new SimpleDateFormat("dd/mm/yyyy");

        // Date
        try {
            formattedDate = DesiredFormat.format(sourceFormat.parse(article.getPubDate().substring(0, 10)));
        } catch (ParseException e) {
            formattedDate = "Unknown";
        }

        // Description
        if (article.getSnippet().equals("")){
            description_text = "Unwritten";
        } else description_text = article.getSnippet();

        // Image
        try {
            url_image = article.getMultimedia().get(2).getUrl();
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
