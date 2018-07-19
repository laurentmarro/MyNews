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
import retrofit2.Call;

public class TopStoriesFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.list) RecyclerView recyclerView;
    @BindView(R.id.fragment_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Call call;
    private List<Result> results;
    private ArticleAdapter adapter;

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }
    public TopStoriesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
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
        this.results = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new ArticleAdapter(this.results, Glide.with(this));
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
        NewsCalls.fetchUserArticle(new NewsCalls.Callbacks() {
            @Override
            public void onResponse(ArticleComposition articleComposition) {
                articleComposition.getResults().get(0).getTitle();
                articleComposition.getResults().get(0).getShortUrl();
                articleComposition.getResults().get(0).getMultimedia().get(0).getUrl();
            }

            @Override
            public void onFailure() {
                Log.e("Error","No new article");
            }
        });

    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<Result> results){
        results.clear();
        results.addAll(results);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }
}