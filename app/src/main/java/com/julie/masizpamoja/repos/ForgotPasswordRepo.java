package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.ForgotPasswordState;
import com.julie.masizpamoja.models.ForgotPassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordRepo {
    private ApiClient mApiClient;

    //constructor
    public ForgotPasswordRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<ForgotPasswordState> forgotPassword(String email) {

        MutableLiveData<ForgotPasswordState> forgotPasswordStateMutableLiveData = new MutableLiveData<>();
        Call<ForgotPassword> call = mApiClient.mApiservice().forgotPassword(email);
        call.enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {
                if (response.code() == 200) {
                    forgotPasswordStateMutableLiveData.setValue(new ForgotPasswordState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        forgotPasswordStateMutableLiveData.setValue(new ForgotPasswordState(response.message()));

                    }else {
                        forgotPasswordStateMutableLiveData.setValue(new ForgotPasswordState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                forgotPasswordStateMutableLiveData.setValue(new ForgotPasswordState(t));
            }
        });

        return forgotPasswordStateMutableLiveData;

    }
}
