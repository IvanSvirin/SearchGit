package com.example.ivansv.searchgit.data.rest;

import com.example.ivansv.searchgit.data.model.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ResponseService {
    @GET("repositories")
    Observable<ApiResponse> getResponse(@Query("q") String query, @Query("page") String page, @Query("per_page") String perPage);
}
