package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.SupportState;
import com.julie.masizpamoja.models.Support;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportRepo {
    private ApiClient mApiClient;

    //constructor
    public SupportRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<SupportState> support(String name, String email, String message, String accessToken) {

        MutableLiveData<SupportState> supportStateMutableLiveData = new MutableLiveData<>();
        Call<Support> call = mApiClient.mApiservice().support(name, email, message, accessToken);
        call.enqueue(new Callback<Support>() {
            @Override
            public void onResponse(Call<Support> call, Response<Support> response) {
                if (response.code() == 200) {
                    supportStateMutableLiveData.setValue(new SupportState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        supportStateMutableLiveData.setValue(new SupportState(response.message()));

                    }else {
                        supportStateMutableLiveData.setValue(new SupportState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<Support> call, Throwable t) {
                supportStateMutableLiveData.setValue(new SupportState(t));
            }
        });

        return supportStateMutableLiveData;

    }
}
