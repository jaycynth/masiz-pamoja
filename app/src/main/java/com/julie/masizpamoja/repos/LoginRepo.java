package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.LoginState;
import com.julie.masizpamoja.models.Login;
import com.julie.masizpamoja.models.LoginError;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    private ApiClient mApiClient;

    //constructor
    public LoginRepo(Application application) {
        mApiClient = new ApiClient(application);

    }

    public LiveData<LoginState> loginUser(String email, String password) {

        MutableLiveData<LoginState> loginStateMutableLiveData = new MutableLiveData<>();
        Call<Login> call = mApiClient.mApiservice().loginUser(email, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.code() == 200) {
                    loginStateMutableLiveData.setValue(new LoginState(response.body()));


                }else if(response.code() == 401 || response.code() == 404 || response.code() == 422){
                    Gson gson = new Gson();
                    Type type = new TypeToken<LoginError>() {}.getType();
                    LoginError loginError = gson.fromJson(response.errorBody().charStream(),type);
                    loginStateMutableLiveData.setValue(new LoginState(loginError));
                }
                else {
                    if (BuildConfig.DEBUG){
                        loginStateMutableLiveData.setValue(new LoginState(response.message()));

                    }else {
                        loginStateMutableLiveData.setValue(new LoginState("An error occurred"));
                    }

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                loginStateMutableLiveData.setValue(new LoginState(t));
            }
        });

        return loginStateMutableLiveData;

    }

}