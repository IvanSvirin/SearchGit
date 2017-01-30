package com.example.ivansv.searchgit.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {
    @SerializedName("items")
    @Expose
    private List<Item> items;

    public ApiResponse(ArrayList<Item> vacancies) {
        this.items = vacancies;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
