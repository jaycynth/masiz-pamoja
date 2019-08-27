package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.UpdatePasswordState;
import com.julie.masizpamoja.repos.UpdatePasswordRepo;

public class ProfileViewModel extends AndroidViewModel {
    private MediatorLiveData<UpdatePasswordState> updatePasswordStateMediatorLiveData;
    private UpdatePasswordRepo updatePasswordRepo;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        updatePasswordStateMediatorLiveData = new MediatorLiveData<>();
        updatePasswordRepo = new UpdatePasswordRepo(application);

    }


    /* update password */
    public LiveData<UpdatePasswordState> getUpdatePasswordResponse() {
        return updatePasswordStateMediatorLiveData;
    }

    public void updatePassword(String currentPassword, String newPassword, String accessToken) {
        LiveData<UpdatePasswordState> updatePasswordStateLiveData = updatePasswordRepo.updatePassword(currentPassword, newPassword, accessToken);

        updatePasswordStateMediatorLiveData.addSource(updatePasswordStateLiveData,
                updatePasswordStateMediatorLiveData -> {
                    if (this.updatePasswordStateMediatorLiveData.hasActiveObservers()) {
                        this.updatePasswordStateMediatorLiveData.removeSource(updatePasswordStateLiveData);
                    }
                    this.updatePasswordStateMediatorLiveData.setValue(updatePasswordStateMediatorLiveData);
                });
    }
}
