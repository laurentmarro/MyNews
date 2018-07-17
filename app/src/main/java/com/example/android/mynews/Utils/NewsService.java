package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.ArticleComposition;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface NewsService {

    String BASE_URL="https://api.nytimes.com/";
    String FRAGMENT_TO_SHOW ="topstories";

    @GET("svc/"+ FRAGMENT_TO_SHOW +"/v2/home.json?api-key=ff58457c72574ee094c10a7b22f5ebc7")
    Call<ArticleComposition> getData();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}