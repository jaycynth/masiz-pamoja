package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.RegisterState;
import com.julie.masizpamoja.models.Register;
import com.julie.masizpamoja.models.RegisterUnprocessableEntity;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepo {


    private ApiClient mApiClient;

    //constructor
    public RegisterRepo(Application application) {
        mApiClient = new ApiClient(application);
    }

    public LiveData<RegisterState> createUser(String email, String password, String name) {
        MutableLiveData<RegisterState> registerStateMutableLiveData;
        registerStateMutableLiveData = new MutableLiveData<>();
        Call<Register> call = mApiClient.mApiservice().createUser(email, password, name);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.code() == 200) {
                    registerStateMutableLiveData.setValue(new RegisterState(response.body()));
                }

                 else if (response.code() == 422) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<RegisterUnprocessableEntity>() {}.getType();
                    RegisterUnprocessableEntity authErrorResponse = gson.fromJson(response.errorBody().charStream(),type);
                    registerStateMutableLiveData.setValue(new RegisterState(authErrorResponse));
                }
               else {
                    if (BuildConfig.DEBUG){
                        registerStateMutableLiveData.setValue(new RegisterState(response.message()));
                    }else {
                        registerStateMutableLiveData.setValue(new RegisterState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                registerStateMutableLiveData.setValue(new RegisterState(t));
            }
        });

        return registerStateMutableLiveData;

    }

}
