package com.prateek.reddit.redditsearch.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.util.Log;

import com.prateek.reddit.redditsearch.GithubSearchRepository;
import com.prateek.reddit.redditsearch.RequestFailure;
import com.prateek.reddit.redditsearch.models.Repo;

public class SearchRepositoriesViewModel extends ViewModel {
    private static final int VISIBLE_THRESHOLD = 5;
    private MutableLiveData<String> queryLiveData;
    public LiveData<PagedList<Repo>> repos;
    LiveData<RequestFailure> networkStatus;


    public SearchRepositoriesViewModel(final GithubSearchRepository searchRepository) {

        queryLiveData = new MutableLiveData<String>();

        repos=Transformations.switchMap(queryLiveData, new Function<String, LiveData<PagedList<Repo>>>() {
            @Override
            public LiveData<PagedList<Repo>> apply(String input) {
                return searchRepository.Search(input);
            }
        });

    }


    /**
     * Search a repository based on a query string.
     */

    public void searchRepo(String query){
        Log.d("HERE123","SearchViewModel");
        queryLiveData.postValue(query);

    }



    /**
     * Get the last query value.
     */

    private String lastQueryValue(){
        return  queryLiveData.getValue();
    }




}
