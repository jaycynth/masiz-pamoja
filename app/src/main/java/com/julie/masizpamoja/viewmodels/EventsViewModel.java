package com.julie.masizpamoja.viewmodels;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.AllEventsState;
import com.julie.masizpamoja.repos.AllEventsRepo;

public class EventsViewModel extends AndroidViewModel {
    private MediatorLiveData<AllEventsState> allEventsStateMediatorLiveData;
    private AllEventsRepo allEventsRepo;

    public EventsViewModel(@NonNull Application application) {
        super(application);
        allEventsStateMediatorLiveData = new MediatorLiveData<>();
        allEventsRepo = new AllEventsRepo(application);

    }


    /* all events */
    public LiveData<AllEventsState> getAllEventsResponse() {
        return allEventsStateMediatorLiveData;
    }

    public void allEvents(String accessToken) {
        LiveData<AllEventsState> allEventsStateLiveData = allEventsRepo.allEvents(accessToken);

        allEventsStateMediatorLiveData.addSource(allEventsStateLiveData,
                allEventsStateMediatorLiveData -> {
                    if (this.allEventsStateMediatorLiveData.hasActiveObservers()) {
                        this.allEventsStateMediatorLiveData.removeSource(allEventsStateLiveData);
                    }
                    this.allEventsStateMediatorLiveData.setValue(allEventsStateMediatorLiveData);
                });
    }

}

