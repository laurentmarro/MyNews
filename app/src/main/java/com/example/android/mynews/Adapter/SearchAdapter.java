package com.example.android.mynews.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.RequestManager;
import com.example.android.mynews.Models.Search;
import com.example.android.mynews.R;
import com.example.android.mynews.ViewHolder.SearchViewHolder;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    // FOR DATA
    private List<Search> articles;
    private RequestManager glide; // Declaring a Glide object

    // CONSTRUCTOR
    public SearchAdapter(List<Search> articles, RequestManager glide) {
        this.articles = articles;
        this.glide = glide;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_article, parent, false);
        return new SearchViewHolder(view);
    }

    // UPDATE VIEW HOLDER
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int position) {
        viewHolder.updateWithArticles(this.articles.get(position),this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public Search getArticle(int position){
        return this.articles.get(position);
    }
}