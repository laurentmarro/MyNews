package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.Result;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsStreams {
    public static Observable<List<Result>> streamFetchArticle(String fragment){
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        return newsService.getData(fragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
