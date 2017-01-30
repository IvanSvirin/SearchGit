package com.example.ivansv.searchgit.data.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {
    private static final String API_BASE_URL = "https://api.github.com/search/";

    public static <S> S createRetrofitService(Class<S> serviceClass) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }

    private static int connectTimeoutInSeconds = 30;
    private static int readTimeoutInSeconds = 30;
    private static int writeTimeoutInSeconds = 30;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(connectTimeoutInSeconds, TimeUnit.SECONDS)
            .readTimeout(readTimeoutInSeconds, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutInSeconds, TimeUnit.SECONDS);
}
