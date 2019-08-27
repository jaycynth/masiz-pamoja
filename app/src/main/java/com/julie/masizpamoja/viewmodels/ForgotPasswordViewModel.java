package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.ForgotPasswordState;
import com.julie.masizpamoja.repos.ForgotPasswordRepo;

public class ForgotPasswordViewModel extends AndroidViewModel {

    private MediatorLiveData<ForgotPasswordState> forgotPasswordStateMediatorLiveData;
    private ForgotPasswordRepo forgotPasswordRepo;

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        forgotPasswordStateMediatorLiveData = new MediatorLiveData<>();
        forgotPasswordRepo = new ForgotPasswordRepo(application);

    }


    /* forgot password */
    public LiveData<ForgotPasswordState> getForgotPasswordResponse() {
        return forgotPasswordStateMediatorLiveData;
    }

    public void forgotPassword(String email) {
        LiveData<ForgotPasswordState> forgotPasswordStateLiveData = forgotPasswordRepo.forgotPassword(email);

        forgotPasswordStateMediatorLiveData.addSource(forgotPasswordStateLiveData,
                forgotPasswordStateMediatorLiveData -> {
                    if (this.forgotPasswordStateMediatorLiveData.hasActiveObservers()) {
                        this.forgotPasswordStateMediatorLiveData.removeSource(forgotPasswordStateLiveData);
                    }
                    this.forgotPasswordStateMediatorLiveData.setValue(forgotPasswordStateMediatorLiveData);
                });
    }
}
