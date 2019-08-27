package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.UpdatePasswordState;
import com.julie.masizpamoja.models.UpdatePassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordRepo {
    private ApiClient mApiClient;

    //constructor
    public UpdatePasswordRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<UpdatePasswordState> updatePassword(String currentPassword, String newPassword, String accessToken) {

        MutableLiveData<UpdatePasswordState> updatePasswordStateMutableLiveData = new MutableLiveData<>();
        Call<UpdatePassword> call = mApiClient.mApiservice().updatePassword(currentPassword, newPassword, accessToken);
        call.enqueue(new Callback<UpdatePassword>() {
            @Override
            public void onResponse(Call<UpdatePassword> call, Response<UpdatePassword> response) {
                if (response.code() == 200) {
                    updatePasswordStateMutableLiveData.setValue(new UpdatePasswordState(response.body()));

                }
                else {
                    if (BuildConfig.DEBUG){
                        updatePasswordStateMutableLiveData.setValue(new UpdatePasswordState(response.message()));

                    }else {
                        updatePasswordStateMutableLiveData.setValue(new UpdatePasswordState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<UpdatePassword> call, Throwable t) {
                updatePasswordStateMutableLiveData.setValue(new UpdatePasswordState(t));
            }
        });

        return updatePasswordStateMutableLiveData;

    }
}
