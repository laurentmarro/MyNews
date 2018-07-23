package com.example.android.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.android.mynews.Adapter.ArticleAdapter;
import com.example.android.mynews.Models.ArticleComposition;
import com.example.android.mynews.Models.Result;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.NewsCalls;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class MostPopularFragment extends Fragment implements NewsCalls.Callbacks {

    // FOR DESIGN
    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.fragment_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private List<Result> articles;
    private ArticleAdapter adapter;

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    public MostPopularFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new ArticleAdapter(this.articles, Glide.with(this));
        // Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        NewsCalls.fetchUserArticle(this,"mostpopular/v2/mostemailed/Sports/30");
    }

    // Override callback methods

    @Override
    public void onResponse(@Nullable ArticleComposition articleComposition) {
        // 2.1 - When getting response, we update UI
        if (articleComposition != null) this.updateUI(articleComposition);
    }

    @Override
    public void onFailure() {
        Log.e("Error : ","An error happened");
    }

    // UPDATE UI
    private void updateUI(ArticleComposition articleComposition) {
        articles.clear();
        articles.addAll(articles);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}