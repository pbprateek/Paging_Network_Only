package com.prateek.reddit.redditsearch.Network;


import com.prateek.reddit.redditsearch.models.GithubSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GithubService {

    @GET("search/repositories?sort=stars")
    Call<GithubSearchResponse> searchRepos(@Query("q") String query,

                                           @Query("page") int page,

                                           @Query("per_page") int itemsPerPage);
}
