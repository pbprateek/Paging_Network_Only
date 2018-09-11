package com.prateek.reddit.redditsearch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.prateek.reddit.redditsearch.Network.GithubService;
import com.prateek.reddit.redditsearch.models.Repo;

public class GithubDataSourceFactory extends DataSource.Factory<Integer,Repo> {
    private final GithubService service;

    private final String queryString;

    private MutableLiveData<GithubDataSource> dataSourceLiveData;



    public GithubDataSourceFactory(GithubService service, String query) {
        this.service = service;
        this.queryString = query;
        dataSourceLiveData=new MutableLiveData<GithubDataSource>();
    }


    @Override
    public DataSource<Integer, Repo> create() {
        GithubDataSource githubDataSource=new GithubDataSource(service,queryString);
        dataSourceLiveData.postValue(githubDataSource);

        return githubDataSource;
    }
}
