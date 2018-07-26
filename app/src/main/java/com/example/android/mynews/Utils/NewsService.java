package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.Result;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsService {

    String BASE_URL="https://api.nytimes.com/";

    @GET("svc/{fragment}")
    Observable<List<Result>> getData(@Path("fragment") String fragment);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}