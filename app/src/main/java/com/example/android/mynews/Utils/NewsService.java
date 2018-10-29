package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.ArticleCompositionBusiness;
import com.example.android.mynews.Models.ArticleCompositionMostPopular;
import com.example.android.mynews.Models.ArticleCompositionTopStories;
import com.example.android.mynews.Models.SearchComposition;
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
    Observable<SearchComposition> getDataSearch(@Url String search);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}