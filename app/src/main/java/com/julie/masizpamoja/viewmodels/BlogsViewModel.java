package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.julie.masizpamoja.datastates.AllBlogsState;
import com.julie.masizpamoja.repos.AllBlogsRepo;

public class BlogsViewModel extends AndroidViewModel {


    private MediatorLiveData<AllBlogsState> allBlogsStateMediatorLiveData;
    private AllBlogsRepo allBlogsRepo;

    public BlogsViewModel(@NonNull Application application) {
        super(application);
        allBlogsStateMediatorLiveData = new MediatorLiveData<>();
        allBlogsRepo = new AllBlogsRepo(application);

    }


    /* all blogs */
    public LiveData<AllBlogsState> getAllBlogsResponse() {
        return allBlogsStateMediatorLiveData;
    }

    public void allBlogs(String accessToken) {
        LiveData<AllBlogsState> allBlogsStateLiveData = allBlogsRepo.allBlogs(accessToken);

        allBlogsStateMediatorLiveData.addSource(allBlogsStateLiveData,
                allBlogsStateMediatorLiveData -> {
                    if (this.allBlogsStateMediatorLiveData.hasActiveObservers()) {
                        this.allBlogsStateMediatorLiveData.removeSource(allBlogsStateLiveData);
                    }
                    this.allBlogsStateMediatorLiveData.setValue(allBlogsStateMediatorLiveData);
                });
    }

}