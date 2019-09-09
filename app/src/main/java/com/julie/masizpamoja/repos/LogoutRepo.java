package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.LogoutState;
import com.julie.masizpamoja.models.Logout;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutRepo {
    private ApiClient mApiClient;

    //constructor
    public LogoutRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<LogoutState> logout(String accessToken) {

        MutableLiveData<LogoutState> loginStateMutableLiveData = new MutableLiveData<>();
        Call<Logout> call = mApiClient.mApiservice().logout(accessToken);
        call.enqueue(new Callback<Logout>() {
            @Override
            public void onResponse(Call<Logout> call, Response<Logout> response) {
                if (response.code() == 200) {
                    loginStateMutableLiveData.setValue(new LogoutState(response.body()));

                } else {
                    if (BuildConfig.DEBUG){
                        loginStateMutableLiveData.setValue(new LogoutState(response.message()));

                    }else {
                        loginStateMutableLiveData.setValue(new LogoutState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<Logout> call, Throwable t) {
                loginStateMutableLiveData.setValue(new LogoutState(t));
            }
        });

        return loginStateMutableLiveData;

    }

}