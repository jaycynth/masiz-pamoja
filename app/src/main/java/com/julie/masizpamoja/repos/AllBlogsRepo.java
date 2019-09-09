package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.AllBlogsState;
import com.julie.masizpamoja.models.AllBlogs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllBlogsRepo {
  private ApiClient mApiClient;

  //constructor
  public AllBlogsRepo(Application application) {
    mApiClient = new ApiClient(application);

  }

  public LiveData<AllBlogsState> allBlogs(String accessToken) {

    MutableLiveData<AllBlogsState> allBlogsStateMutableLiveData = new MutableLiveData<>();
    Call<AllBlogs> call = mApiClient.mApiservice().getAllBlogs(accessToken);
    call.enqueue(new Callback<AllBlogs>() {
      @Override
      public void onResponse(Call<AllBlogs> call, Response<AllBlogs> response) {
        if (response.code() == 200) {
          allBlogsStateMutableLiveData.setValue(new AllBlogsState(response.body()));

        } else {
          if (BuildConfig.DEBUG){
            allBlogsStateMutableLiveData.setValue(new AllBlogsState(response.message()));

          }else {
            allBlogsStateMutableLiveData.setValue(new AllBlogsState("An error occurred"));
          }

        }
      }

      @Override
      public void onFailure(Call<AllBlogs> call, Throwable t) {
        allBlogsStateMutableLiveData.setValue(new AllBlogsState(t));
      }
    });

    return allBlogsStateMutableLiveData;

  }
}