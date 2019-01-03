package com.example.android.mynews.Controllers.Helpers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.android.mynews.Adapter.ArticleSearchAdapter;
import com.example.android.mynews.Models.SearchModels.ArticleCompositionSearch;
import com.example.android.mynews.Models.SearchModels.ArticleSearch;
import com.example.android.mynews.R;
import com.example.android.mynews.Utils.NewsStreams;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationResearch extends AppCompatActivity {

    //FOR DATA
    private Disposable disposable;
    private SharedPreferences preferences;
    private List<ArticleSearch> articles;
    private ArticleSearchAdapter adapter;
    private String origin;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        origin = preferences.getString("ORIGINE", getString(R.string.search));
        executeHttpRequestWithRetrofit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        // bring back URL
        String urlToShow = preferences.getString("URLTOSHOW", "");

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
                        if  (articles.size() > 0) {
                            Intent intent = new Intent(getApplicationContext(), SendNotifications.class);
                            startActivity(intent);
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
    }

}