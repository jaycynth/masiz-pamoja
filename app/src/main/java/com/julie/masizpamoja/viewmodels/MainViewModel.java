package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.LogoutState;
import com.julie.masizpamoja.repos.LogoutRepo;

public class MainViewModel extends AndroidViewModel {

    private MediatorLiveData<LogoutState> logoutStateMediatorLiveData;
    private LogoutRepo logoutRepo;


    public MainViewModel(@NonNull Application application) {
        super(application);
        logoutStateMediatorLiveData = new MediatorLiveData<>();
        logoutRepo = new LogoutRepo(application);
        
    }



    public LiveData<LogoutState> getLogoutResponse(){
        return logoutStateMediatorLiveData;
    }

    public void userLogout(String accessToken){

        LiveData<LogoutState> logoutStateLiveData = logoutRepo.logout(accessToken);
        logoutStateMediatorLiveData.addSource(logoutStateLiveData,
                logoutStateMediatorLiveData -> {
                    if (this.logoutStateMediatorLiveData.hasActiveObservers()){
                        this.logoutStateMediatorLiveData.removeSource(logoutStateLiveData);
                    }
                    this.logoutStateMediatorLiveData.setValue(logoutStateMediatorLiveData);
                });

    }
   
}
