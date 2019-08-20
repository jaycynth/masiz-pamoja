package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.LoginState;
import com.julie.masizpamoja.repos.LoginRepo;

public class LoginViewModel extends AndroidViewModel {

    private MediatorLiveData<LoginState> loginStateMediatorLiveData;
    private LoginRepo loginRepo;

    public LoginViewModel(Application application){
        super(application);
        loginStateMediatorLiveData = new MediatorLiveData<>();
        loginRepo = new LoginRepo(application);
    }

    public LiveData<LoginState> getLoginResponse(){
        return loginStateMediatorLiveData;
    }

    public void userLogin(String email, String password){

        LiveData<LoginState> loginStateLiveData = loginRepo.loginUser(email, password);
        loginStateMediatorLiveData.addSource(loginStateLiveData,
                loginStateMediatorLiveData -> {
                    if (this.loginStateMediatorLiveData.hasActiveObservers()){
                        this.loginStateMediatorLiveData.removeSource(loginStateLiveData);
                    }
                    this.loginStateMediatorLiveData.setValue(loginStateMediatorLiveData);
                });

    }


}
