package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.LatestBlogsState;
import com.julie.masizpamoja.repos.LatestBlogsRepo;

public class HomeViewModel extends AndroidViewModel {

    private MediatorLiveData<LatestBlogsState> latestBlogsStateMediatorLiveData;
    private LatestBlogsRepo latestBlogsRepo;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        latestBlogsStateMediatorLiveData = new MediatorLiveData<>();
        latestBlogsRepo = new LatestBlogsRepo(application);
    }

    /* latest blogs */
    public LiveData<LatestBlogsState> getLatestBlogsResponse() {
        return latestBlogsStateMediatorLiveData;
    }

    public void latestBlogs(String accessToken) {
        LiveData<LatestBlogsState> latestBlogsStateLiveData = latestBlogsRepo.latestBlogs(accessToken);

        latestBlogsStateMediatorLiveData.addSource(latestBlogsStateLiveData,
                latestBlogsStateMediatorLiveData -> {
                    if (this.latestBlogsStateMediatorLiveData.hasActiveObservers()) {
                        this.latestBlogsStateMediatorLiveData.removeSource(latestBlogsStateLiveData);
                    }
                    this.latestBlogsStateMediatorLiveData.setValue(latestBlogsStateMediatorLiveData);
                });
    }
}