package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.RegisterState;
import com.julie.masizpamoja.repos.RegisterRepo;

public class RegisterViewModel extends AndroidViewModel {
    private MediatorLiveData<RegisterState> registerStateMediatorLiveData;
    private RegisterRepo registerRepo;


    public RegisterViewModel(Application application){
        super(application);
        registerStateMediatorLiveData = new MediatorLiveData<>();
        registerRepo = new RegisterRepo(application);
    }

    public LiveData<RegisterState> getRegisterResponse(){
        return registerStateMediatorLiveData;
    }

    public void createUser(String email, String password, String name){
        LiveData<RegisterState> registerStateLiveData = registerRepo.createUser(email, password ,name);
        registerStateMediatorLiveData.addSource(registerStateLiveData,
                registerStateMediatorLiveData -> {
                    if (this.registerStateMediatorLiveData.hasActiveObservers()){
                        this.registerStateMediatorLiveData.removeSource(registerStateLiveData);
                    }
                    this.registerStateMediatorLiveData.setValue(registerStateMediatorLiveData);
                });

    }

}
