package com.prateek.reddit.redditsearch.Network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {

    public static final String LIVE_BASE_URL = "https://api.github.com/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {

            //Initializing HttpLoggingInterceptor to receive the HTTP event logs

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);



            //Building the HTTPClient with the logger

            OkHttpClient httpClient = new OkHttpClient.Builder()

                    .addInterceptor(loggingInterceptor)

                    .build();



            retrofit = new Retrofit.Builder()
                    .baseUrl(LIVE_BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
