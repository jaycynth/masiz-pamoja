package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.LatestBlogsState;
import com.julie.masizpamoja.models.LatestBlogs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestBlogsRepo {

    private ApiClient mApiClient;

    //constructor
    public LatestBlogsRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<LatestBlogsState> latestBlogs(String accessToken) {

        MutableLiveData<LatestBlogsState> latestBlogsStateMutableLiveData = new MutableLiveData<>();
        Call<LatestBlogs> call = mApiClient.mApiservice().getLatestBlogs(accessToken);
        call.enqueue(new Callback<LatestBlogs>() {
            @Override
            public void onResponse(Call<LatestBlogs> call, Response<LatestBlogs> response) {
                if (response.code() == 200) {
                    latestBlogsStateMutableLiveData.setValue(new LatestBlogsState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        latestBlogsStateMutableLiveData.setValue(new LatestBlogsState(response.message()));

                    }else {
                        latestBlogsStateMutableLiveData.setValue(new LatestBlogsState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<LatestBlogs> call, Throwable t) {
                latestBlogsStateMutableLiveData.setValue(new LatestBlogsState(t));
            }
        });

        return latestBlogsStateMutableLiveData;

    }
}