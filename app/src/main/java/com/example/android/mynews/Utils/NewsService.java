package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.ArticleComposition;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsService {
    String BASE_URL="https://api.nytimes.com/";

    @GET("svc/{fragmentToShow}.json?api-key=ff58457c72574ee094c10a7b22f5ebc7")
    Call<ArticleComposition> getData(@Path("fragmentToShow") String fragmentToShow);

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}