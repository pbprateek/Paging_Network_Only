package com.prateek.reddit.redditsearch.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubSearchResponse {

    @SerializedName("total_count")
    @Expose
    private Long totalCount;

    @SerializedName("items")
    @Expose
    private List<Repo> items = null;


    public List<Repo> getItems() {
        return items;
    }


}
