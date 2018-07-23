package com.example.android.mynews.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.RequestManager;
import com.example.android.mynews.Models.Result;
import com.example.android.mynews.R;
import com.example.android.mynews.ViewHolder.ArticleViewHolder;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    // FOR DATA
    private List<Result> articles;
    private RequestManager glide;

    // CONSTRUCTOR
    public ArticleAdapter(List<Result> articles, RequestManager glide) {

        this.articles = articles;
        this.glide = glide;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listarticle = inflater.inflate(R.layout.list_article, parent, false);

        return new ArticleViewHolder(listarticle);
    }

    // UPDATE VIEW HOLDER
    @Override
    public void onBindViewHolder(ArticleViewHolder viewHolder, int position) {
        viewHolder.update(this.articles.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.articles.size();
    }
}