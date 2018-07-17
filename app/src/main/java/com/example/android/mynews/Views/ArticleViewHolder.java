package com.example.android.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.android.mynews.Models.Result;
import com.example.android.mynews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title) TextView textView;
    @BindView(R.id.description) TextView twolines;
    @BindView(R.id.articleimage) ImageView imageView;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void update(Result result, RequestManager glide){
        this.textView.setText(result.getTitle());
        this.twolines.setText(result.getShortUrl());
        glide.load(result.getMultimedia().get(0).getUrl()).into(imageView);
    }
}