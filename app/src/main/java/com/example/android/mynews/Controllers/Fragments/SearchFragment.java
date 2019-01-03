package com.example.android.mynews.Controllers.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import com.bumptech.glide.Glide;
import com.example.android.mynews.Adapter.ArticleSearchAdapter;
import com.example.android.mynews.Controllers.Activities.ArticleActivity;
import com.example.android.mynews.Controllers.Activities.SearchActivity;
import com.example.android.mynews.Models.SearchModels.ArticleCompositionSearch;
import com.example.android.mynews.Models.SearchModels.ArticleSearch;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.ItemClickSupport;
import com.example.android.mynews.Utils.NewsStreams;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchFragment extends Fragment {

    @NonNull
    public static SearchFragment newInstance() {
        return (new SearchFragment());
    }

    // FOR DESIGN
    @BindView(R.id.search_recycler_view)
    RecyclerView recyclerView; // Declare RecyclerView
    @BindView(R.id.search_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout; // Declare the SwipeRefreshLayout

    //FOR DATA
    private Disposable disposable;
    private SharedPreferences preferences;
    private List<ArticleSearch> articles;
    private ArticleSearchAdapter adapter;
    private String origin;
    private String urlToShow;

    public SearchFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        origin = preferences.getString("ORIGINE", getString(R.string.search));
        {   View view = inflater.inflate(R.layout.fragment_search, container, false);
            ButterKnife.bind(this, view);
            this.configureRecyclerView(); // Call during UI creation
            this.configureSwipeRefreshLayout(); // Configure the SwipeRefreshLayout
            this.configureOnClickRecyclerView(); // Calling the method that configuring click on RecyclerView
            this.executeHttpRequestWithRetrofit(); // Execute stream after UI creation
            return view;
        }
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
                        ArticleSearch articleSearch = adapter.getArticle(position);
                        Intent intent = new Intent(getActivity(), ArticleActivity.class);
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
        this.adapter = new ArticleSearchAdapter(this.articles, Glide.with(this));
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

    private void executeHttpRequestWithRetrofit() {
        // bring back URL

        if (origin.equals(getString(R.string.search))) {
            urlToShow = preferences.getString("URLTOSEARCH", "");
        } else {
            urlToShow = preferences.getString("URLTOSHOW", "");
        }

        // Execute the stream subscribing to Observable defined inside NewsStreams
        this.disposable = NewsStreams
                .streamFetchArticleSearch(urlToShow)
                .subscribeWith(new DisposableObserver<ArticleCompositionSearch>() {
                    @Override
                    public void onNext(ArticleCompositionSearch articleComposition) {
                        // Update UI with list of articles
                        updateUI(articleComposition);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Search Fragment : ", "On Error" + Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        if (articles.size() == 0 && (origin.equals(getString(R.string.search)))) {
                            popUp();
                        }
                    }
                });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(ArticleCompositionSearch articleComposition){
        articles.clear();
        articles.addAll(articleComposition.getResponse().getDocs());
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    // -------------------
    // POP UP
    // -------------------

    private void popUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.sorry_search);
        builder.setCancelable(false);
        builder.setNeutralButton(
                getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        startActivity(intent);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        Button neutralButton = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        alert.getWindow();

        // Setting Dialog View
        Window window = alert.getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER);
        alert.show();
    }
}