package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.AllContactListState;
import com.julie.masizpamoja.datastates.HelpDeskState;
import com.julie.masizpamoja.repos.AllContactListRepo;
import com.julie.masizpamoja.repos.HelpDeskRepo;

public class HelpDeskViewModel extends AndroidViewModel {
    private MediatorLiveData<HelpDeskState> helpDeskStateMediatorLiveData;
    private HelpDeskRepo helpDeskRepo;

    private MediatorLiveData<AllContactListState> allContactListStateMediatorLiveData;
    private AllContactListRepo allContactListRepo;

    public HelpDeskViewModel(@NonNull Application application) {
        super(application);
        helpDeskStateMediatorLiveData = new MediatorLiveData<>();
        helpDeskRepo = new HelpDeskRepo(application);

        allContactListStateMediatorLiveData = new MediatorLiveData<>();
        allContactListRepo = new AllContactListRepo(application);

    }


    /* all help desk details */
    public LiveData<HelpDeskState> getHelpDeskResponse() {
        return helpDeskStateMediatorLiveData;
    }

    public void helpDesk(String accessToken) {
        LiveData<HelpDeskState> helpDeskStateLiveData = helpDeskRepo.helpDesk(accessToken);

        helpDeskStateMediatorLiveData.addSource(helpDeskStateLiveData,
                helpDeskStateMediatorLiveData -> {
                    if (this.helpDeskStateMediatorLiveData.hasActiveObservers()) {
                        this.helpDeskStateMediatorLiveData.removeSource(helpDeskStateLiveData);
                    }
                    this.helpDeskStateMediatorLiveData.setValue(helpDeskStateMediatorLiveData);
                });
    }

    /* all contact list details */
    public LiveData<AllContactListState> getAllContactListResponse() {
        return allContactListStateMediatorLiveData;
    }

    public void allContacts(String accessToken) {
        LiveData<AllContactListState> allContactListStateLiveData = allContactListRepo.allContactList(accessToken);

        allContactListStateMediatorLiveData.addSource(allContactListStateLiveData,
                allContactListStateMediatorLiveData -> {
                    if (this.allContactListStateMediatorLiveData.hasActiveObservers()) {
                        this.allContactListStateMediatorLiveData.removeSource(allContactListStateLiveData);
                    }
                    this.allContactListStateMediatorLiveData.setValue(allContactListStateMediatorLiveData);
                });
    }

}
