package com.julie.masizpamoja.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.julie.masizpamoja.models.SavedMessage;
import com.julie.masizpamoja.repos.SavedMessageRepo;

import java.util.List;

public class SavedMessageViewModel extends AndroidViewModel {

    private LiveData<List<SavedMessage>> listLiveData;

    private SavedMessageRepo savedMessageRepo;

    public SavedMessageViewModel(@NonNull Application application) {
        super(application);
        savedMessageRepo = new SavedMessageRepo();
        listLiveData = savedMessageRepo.getAllMessages();
    }

    public LiveData<List<SavedMessage>> getListLiveData() {
        return listLiveData;
    }


    public void saveMessage(SavedMessage savedMessage) {
        savedMessageRepo.saveMessage(savedMessage);
    }

    public  void deleteMessage(SavedMessage savedMessage){
        savedMessageRepo.deleteMessage(savedMessage);
    }
}
