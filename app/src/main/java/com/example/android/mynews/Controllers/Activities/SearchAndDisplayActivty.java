package com.example.android.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.android.mynews.Adapter.SearchAdapter;
import com.example.android.mynews.Models.Search;
import com.example.android.mynews.Models.SearchComposition;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.ItemClickSupport;
import com.example.android.mynews.Utils.NewsStreams;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchAndDisplayActivty extends AppCompatActivity {

    // FOR DESIGN
    @BindView(R.id.search_and_display_recycler_view) RecyclerView recyclerView; // Declare RecyclerView
    @BindView(R.id.search_and_display_swipe_container) SwipeRefreshLayout swipeRefreshLayout; // Declare the SwipeRefreshLayout

    //FOR DATA
    private Disposable disposable;
    // Declare list and Adapter
    private List<Search> articles;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_display_activty);
        this.configureRecyclerView(); // Call during UI creation
        this.configureSwipeRefreshLayout(); // Configure the SwipeRefreshLayout
        this.configureOnClickRecyclerView(); // Calling the method that configuring click on RecyclerView
        this.executeHttpRequestWithRetrofit(); // Execute stream after UI creation
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
                        Search search = adapter.getArticle(position);
                        Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
                        intent.putExtra("URL", articles.get(position).getWebUrl());
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
        this.adapter = new SearchAdapter(this.articles, Glide.with(this));
        // Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateSearchParameters();
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void updateSearchParameters(){

    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        // Execute the stream subscribing to Observable defined inside NewsStreams
        this.disposable = NewsStreams
                .streamFetchSearch("")
                .subscribeWith(new DisposableObserver<SearchComposition>()
                {
                    @Override
                    public void onNext(SearchComposition searchComposition) {
                        // Update UI with list of articles
                        updateUI(searchComposition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Search : ","On Error"+Log.getStackTraceString(e));
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

    private void updateUI(SearchComposition searchComposition){
        swipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(searchComposition.getDocs());
        adapter.notifyDataSetChanged();
    }
}