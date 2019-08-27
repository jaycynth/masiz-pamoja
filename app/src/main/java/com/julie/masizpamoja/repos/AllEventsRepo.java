package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.AllEventsState;
import com.julie.masizpamoja.models.AllEvents;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllEventsRepo {

    private ApiClient mApiClient;

    //constructor
    public AllEventsRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<AllEventsState> allEvents(String accessToken) {

        MutableLiveData<AllEventsState> allEventsStateMutableLiveData = new MutableLiveData<>();
        Call<AllEvents> call = mApiClient.mApiservice().getAllEvents(accessToken);
        call.enqueue(new Callback<AllEvents>() {
            @Override
            public void onResponse(Call<AllEvents> call, Response<AllEvents> response) {
                if (response.code() == 200) {
                    allEventsStateMutableLiveData.setValue(new AllEventsState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        allEventsStateMutableLiveData.setValue(new AllEventsState(response.message()));

                    }else {
                        allEventsStateMutableLiveData.setValue(new AllEventsState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<AllEvents> call, Throwable t) {
                allEventsStateMutableLiveData.setValue(new AllEventsState(t));
            }
        });

        return allEventsStateMutableLiveData;

    }
}
