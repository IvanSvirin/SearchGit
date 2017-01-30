package com.example.ivansv.searchgit.data.rest;

import com.example.ivansv.searchgit.data.model.ApiResponse;
import com.example.ivansv.searchgit.data.model.Item;

import java.util.List;

import rx.Observable;

public class ResponseManager {
    private static volatile ResponseManager client;

    public static ResponseManager getInstance() {
        if (client == null) {
            synchronized (ResponseManager.class) {
                if (client == null) {
                    client = new ResponseManager();
                }
            }
        }
        return client;
    }

    private Observable<ApiResponse> getApiResponseObservable(String query, int offset, int limit) {
        ResponseService responseService = ServiceFactory.createRetrofitService(ResponseService.class);
        return responseService.getResponse(query, String.valueOf(offset / limit + 1), String.valueOf(limit));
    }

    public Observable<List<Item>> getResponse(String query, int offset, int limit) {
        return getApiResponseObservable(query, offset, limit)
                .flatMap(response -> {
                    return Observable.just(response.getItems());
                });
    }
}
