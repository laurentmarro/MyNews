package com.example.android.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.android.mynews.Adapter.ArticleMostPopularAdapter;
import com.example.android.mynews.Controllers.Activities.ArticleActivity;
import com.example.android.mynews.Models.MostPopularModels.ArticleCompositionMostPopular;
import com.example.android.mynews.Models.MostPopularModels.ArticleMostPopular;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.ItemClickSupport;
import com.example.android.mynews.Utils.NewsStreams;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends Fragment {

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    // FOR DESIGN
    @BindView(R.id.mostpopular_recycler_view)
    RecyclerView recyclerView; // Declare RecyclerView
    @BindView(R.id.mostpopular_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout; // Declare the SwipeRefreshLayout

    //FOR DATA
    private Disposable disposable;
    // Declare list and Adapter
    private List<ArticleMostPopular> articles;
    private ArticleMostPopularAdapter adapter;

    public MostPopularFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView(); // Call during UI creation
        this.configureSwipeRefreshLayout(); // Configure the SwipeRefreshLayout
        this.configureOnClickRecyclerView(); // Calling the method that configuring click on RecyclerView
        this.executeHttpRequestWithRetrofit(); // Execute stream after UI creation
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTION
    // -----------------

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.list_article)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        ArticleMostPopular articleMostPopular = adapter.getArticle(position);
                        Intent intent = new Intent(getActivity(), ArticleActivity.class);
                        intent.putExtra("URL", articles.get(position).getUrl());
                        startActivity(intent);
                    }
                });
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // Reset lists
        this.articles = new ArrayList<>();
        // Create adapter passing the list of articles
        this.adapter = new ArticleMostPopularAdapter(this.articles, Glide.with(this));
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
        // Execute the stream subscribing to Observable defined inside NewsStreams
        this.disposable = NewsStreams
                .streamFetchArticleMostPopular("mostpopular/v2/mostemailed/all-sections/1.json?api-key=ff58457c72574ee094c10a7b22f5ebc7")
                .subscribeWith(new DisposableObserver<ArticleCompositionMostPopular>()
                {
                    @Override
                    public void onNext(ArticleCompositionMostPopular articleComposition) {
                        // Update UI with list of articles
                        updateUI(articleComposition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TopStories : ","On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() { }
                });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(ArticleCompositionMostPopular articleComposition){
        swipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(articleComposition.getResults());
        adapter.notifyDataSetChanged();
    }
}
