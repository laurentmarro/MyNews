package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.BusinessModels.ArticleCompositionBusiness;
import com.example.android.mynews.Models.MostPopularModels.ArticleCompositionMostPopular;
import com.example.android.mynews.Models.TopStoriesModels.ArticleCompositionTopStories;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsStreams {
    public static Observable<ArticleCompositionTopStories> streamFetchArticle(String fragment){
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        return newsService.getData(fragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleCompositionMostPopular> streamFetchArticleMostPopular(String mostpopularfragment){
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        return newsService.getDataMostPopular(mostpopularfragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<ArticleCompositionBusiness> streamFetchArticleBusiness(String businessfragment){
        NewsService newsService = NewsService.retrofit.create(NewsService.class);
        return newsService.getDataBusiness(businessfragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

}