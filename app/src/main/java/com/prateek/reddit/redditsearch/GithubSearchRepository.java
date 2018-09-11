package com.prateek.reddit.redditsearch;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.prateek.reddit.redditsearch.Network.GithubService;
import com.prateek.reddit.redditsearch.models.Repo;

import java.util.concurrent.Executor;

public class GithubSearchRepository {

    private GithubService service;
    private Executor executor;


    public GithubSearchRepository(GithubService service, Executor executor){
        this.service=service;
        this.executor=executor;
    }

    public LiveData<PagedList<Repo>> Search(String query){
        GithubDataSourceFactory dataSourceFactory = new GithubDataSourceFactory(service, query);



        // Configure paging

        PagedList.Config config = new PagedList.Config.Builder()

                // Number of items to fetch at once. [Required]

                .setPageSize(30)

                // Number of items to fetch on initial load. Should be greater than Page size. [Optional]

                .setInitialLoadSizeHint(40)

                .setEnablePlaceholders(true) // Show empty views until data is available

                .build();



        // Build PagedList

        LiveData<PagedList<Repo>> list =

                new LivePagedListBuilder(dataSourceFactory, config) // Can pass `pageSize` directly instead of `config`

                        // Do fetch operations on the main thread. We'll instead be using Retrofit's

                        // built-in enqueue() method for background api calls.

                        .setFetchExecutor(executor)

                        .build();

        Log.d("HERE123","Repo");


        return list;

    }
}
