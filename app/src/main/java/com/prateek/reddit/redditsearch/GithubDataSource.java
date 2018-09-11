package com.prateek.reddit.redditsearch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.prateek.reddit.redditsearch.Network.GithubService;
import com.prateek.reddit.redditsearch.models.GithubSearchResponse;
import com.prateek.reddit.redditsearch.models.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class GithubDataSource extends PageKeyedDataSource<Integer, Repo> {


    private final GithubService service;

    private final String queryString;

    private final MutableLiveData<RequestFailure> requestFailureLiveData;


    public GithubDataSource(GithubService service, String query) {
        this.service = service;
        this.queryString = query;
        this.requestFailureLiveData = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Repo> callback) {

        final int page = 1;

        Call<GithubSearchResponse> call = service.searchRepos(queryString, page, 30);
        call.enqueue(new Callback<GithubSearchResponse>() {
            @Override
            public void onResponse(Call<GithubSearchResponse> call, Response<GithubSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {

                        onFailure(call, new HttpException(response));

                        return;

                    }

                    List<Repo> repos = response.body().getItems();
                    Log.d("HERE123","DataSucess");
                    callback.onResult(repos,null,page+1);


                }else {
                    onFailure(call, new HttpException(response));

                    return;
                }

            }

            @Override
            public void onFailure(Call<GithubSearchResponse> call, Throwable t) {

                // Allow user to retry the failed request

                Retryable retryable = new Retryable() {

                    @Override

                    public void retry() {

                        loadInitial(params, callback);

                    }

                };



                handleError(retryable, t);

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Repo> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Repo> callback) {

        final int page=params.key;
        Call<GithubSearchResponse> call = service.searchRepos(queryString, page, 30);
        call.enqueue(new Callback<GithubSearchResponse>() {
            @Override
            public void onResponse(Call<GithubSearchResponse> call, Response<GithubSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {

                        onFailure(call, new HttpException(response));

                        return;

                    }

                    List<Repo> repos = response.body().getItems();
                    Log.d("HERE123","DAtaAfter");
                    callback.onResult(repos,page+1);


                }else {
                    onFailure(call, new HttpException(response));

                    return;
                }

            }

            @Override
            public void onFailure(Call<GithubSearchResponse> call, Throwable t) {

                // Allow user to retry the failed request

                Retryable retryable = new Retryable() {

                    @Override

                    public void retry() {

                        loadAfter(params, callback);

                    }

                };



                handleError(retryable, t);

            }
        });

    }

    private void handleError(Retryable retryable,Throwable throwable){

        requestFailureLiveData.postValue(new RequestFailure(retryable, throwable.getMessage()));

    }

    public LiveData<RequestFailure> getRequestFailureLiveData() {

        return requestFailureLiveData;

    }
}
