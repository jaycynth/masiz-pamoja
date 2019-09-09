package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.SupportState;
import com.julie.masizpamoja.repos.SupportRepo;

public class SupportViewModel extends AndroidViewModel {
    private MediatorLiveData<SupportState> supportStateMediatorLiveData;
    private SupportRepo supportRepo;

    public SupportViewModel(@NonNull Application application) {
        super(application);
        supportStateMediatorLiveData = new MediatorLiveData<>();
        supportRepo = new SupportRepo(application);

    }


    /* support */
    public LiveData<SupportState> getSupportResponse() {
        return supportStateMediatorLiveData;
    }

    public void support(String name, String email, String message, String accessToken) {
        LiveData<SupportState> supportStateLiveData = supportRepo.support(name,email,message, accessToken);

        supportStateMediatorLiveData.addSource(supportStateLiveData,
                supportStateMediatorLiveData -> {
                    if (this.supportStateMediatorLiveData.hasActiveObservers()) {
                        this.supportStateMediatorLiveData.removeSource(supportStateLiveData);
                    }
                    this.supportStateMediatorLiveData.setValue(supportStateMediatorLiveData);
                });
    }

}