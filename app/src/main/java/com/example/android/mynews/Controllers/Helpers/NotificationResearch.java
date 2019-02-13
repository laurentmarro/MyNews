package com.example.android.mynews.Controllers.Helpers;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import com.example.android.mynews.Models.SearchModels.ArticleCompositionSearch;
import com.example.android.mynews.Models.SearchModels.ArticleSearch;
import com.example.android.mynews.Utils.NewsStreams;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class NotificationResearch extends Service {

    //FOR DATA
    private SharedPreferences preferences;
    String filter, urlToShow, today, day, origin;
    int myDay;
    List<ArticleSearch> articles = new ArrayList<>();
    Disposable disposable;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();
        origin = "Notifications";
        editor.putString("ORIGIN", origin);
        editor.apply();
        this.createUrl();
        this.executeHttpRequestWithRetrofit();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    private void createUrl(){
        // bring back URL and filter
        urlToShow = preferences.getString("URLTOSHOW", "");
        filter = preferences.getString("FILTER", "");

        // Updating URL
        Date date = new Date();
        today = new SimpleDateFormat("yyyyMMdd").format(date);
        day = today.substring(6);
        myDay = Integer.parseInt(day)-1;
        if (myDay<10) {
            day ="0"+ String.valueOf(myDay);
        } else {
            day = String.valueOf(myDay);
        }
        today = today.substring(0,6) + day;
        urlToShow = urlToShow+filter+")&begin_date="+today+"&end_date="+today;
        Log.i("TAG", ""+urlToShow);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {

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
                    }

                    @Override
                    public void onComplete() {
                        if (articles.size() != 0) {
                            Intent intent = new Intent(NotificationResearch.this, SendNotifications.class);
                            startService(intent);
                        } else {
                            Log.i("TAG", "onComplete: "+articles.size());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(ArticleCompositionSearch articleComposition){
        articles.addAll(articleComposition.getResponse().getDocs());
    }
}