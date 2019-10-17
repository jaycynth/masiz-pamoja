package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.SentMessageState;
import com.julie.masizpamoja.models.SentMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentMessageRepo {

    private ApiClient mApiClient;

    //constructor
    public SentMessageRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<SentMessageState> postMessage(String message, String accessToken) {

        final MutableLiveData<SentMessageState> sentMessageStateMutableLiveData = new MutableLiveData<>();
        Call<SentMessage> call = mApiClient.mApiservice().postMessage(message, accessToken);
        call.enqueue(new Callback<SentMessage>() {
            @Override
            public void onResponse(Call<SentMessage> call, Response<SentMessage> response) {
                if (response.code() == 200) {
                    sentMessageStateMutableLiveData.setValue(new SentMessageState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        sentMessageStateMutableLiveData.setValue(new SentMessageState(response.message()));

                    }else {
                        sentMessageStateMutableLiveData.setValue(new SentMessageState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<SentMessage> call, Throwable t) {
                sentMessageStateMutableLiveData.setValue(new SentMessageState(t));
            }
        });

        return sentMessageStateMutableLiveData;

    }

}
