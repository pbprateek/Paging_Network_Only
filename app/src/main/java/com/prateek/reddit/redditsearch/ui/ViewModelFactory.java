package com.prateek.reddit.redditsearch.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.prateek.reddit.redditsearch.GithubSearchRepository;


public class ViewModelFactory implements ViewModelProvider.Factory {
    private GithubSearchRepository searchRepository;
    public ViewModelFactory(GithubSearchRepository repository){
        this.searchRepository=repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new SearchRepositoriesViewModel(searchRepository);
    }
}
