package com.example.android.mynews.Utils;

import com.example.android.mynews.Models.ArticleComposition;
import com.example.android.mynews.Models.Result;
import java.lang.ref.WeakReference;
import java.util.List;
import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsCalls {

    // 1 - Creating a callback
    public interface Callbacks {
        void onResponse(@Nullable ArticleComposition articleComposition);
        void onFailure();
    }

    String reponse;

    // 2 - Public method to start fetching articles
    public static void fetchUserArticle(Callbacks callbacks){

        // 2.1 - Create a weak reference to callback (avoid memory leaks)
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // 2.2 - Get a Retrofit instance and the related endpoints
        NewsService newsService = NewsService.retrofit.create(NewsService.class);

        // 2.3 - Create the call on NYT API

        Call<ArticleComposition> call = newsService.getData();

        // 2.4 - Start the call
        call.enqueue(new Callback<ArticleComposition>() {

            @Override
            public void onResponse(Call<ArticleComposition> call, Response<ArticleComposition> response) {
                // 2.5 - Call the proper callback used in controller
                if (callbacksWeakReference.get() != null)
                    callbacksWeakReference.get().onResponse(response.body());

                List<Result> resultList = response.body().getResults();
                for (int i = 0; i < resultList.size(); i++) {
                    String resultats = resultList.get(i).getMultimedia().get(0).getUrl() + " "
                            + resultList.get(i).getShortUrl()+ " "
                            + resultList.get(i).getTitle();
                }
            }

            @Override
            public void onFailure(Call<ArticleComposition> call, Throwable t) {
                // 2.5 - Call the proper callback used in controller (MainFragment)
                if (callbacksWeakReference.get() != null) callbacksWeakReference.get().onFailure();
            }
        });
    }
}