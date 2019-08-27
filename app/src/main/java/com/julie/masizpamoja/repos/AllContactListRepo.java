package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.AllContactListState;
import com.julie.masizpamoja.models.AllContactList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllContactListRepo {
    private ApiClient mApiClient;

    //constructor
    public AllContactListRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<AllContactListState> allContactList(String accessToken) {

        MutableLiveData<AllContactListState> allContactListStateMutableLiveData = new MutableLiveData<>();
        Call<AllContactList> call = mApiClient.mApiservice().getAllContactList(accessToken);
        call.enqueue(new Callback<AllContactList>() {
            @Override
            public void onResponse(Call<AllContactList> call, Response<AllContactList> response) {
                if (response.code() == 200) {
                    allContactListStateMutableLiveData.setValue(new AllContactListState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        allContactListStateMutableLiveData.setValue(new AllContactListState(response.message()));

                    }else {
                        allContactListStateMutableLiveData.setValue(new AllContactListState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<AllContactList> call, Throwable t) {
                allContactListStateMutableLiveData.setValue(new AllContactListState(t));
            }
        });

        return allContactListStateMutableLiveData;

    }
}
