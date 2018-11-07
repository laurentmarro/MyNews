package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.BusinessModels.ArticleCompositionBusiness;
import com.example.android.mynews.Models.MostPopularModels.ArticleCompositionMostPopular;
import com.example.android.mynews.Models.SearchModels.ArticleCompositionSearch;
import com.example.android.mynews.Models.TopStoriesModels.ArticleCompositionTopStories;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET
    Observable<ArticleCompositionTopStories> getData(@Url String fragment);

    @GET
    Observable<ArticleCompositionMostPopular> getDataMostPopular(@Url String fragment);

    @GET
    Observable<ArticleCompositionBusiness> getDataBusiness(@Url String fragment);

    @GET
    Observable<ArticleCompositionSearch> getDataSearch(@Url String fragment);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}