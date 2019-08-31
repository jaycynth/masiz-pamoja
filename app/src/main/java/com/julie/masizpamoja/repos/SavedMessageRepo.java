package com.julie.masizpamoja.repos;

import androidx.lifecycle.LiveData;


import com.julie.masizpamoja.database.AppDatabase;
import com.julie.masizpamoja.database.MessageDao;
import com.julie.masizpamoja.models.SavedMessage;
import com.julie.masizpamoja.utils.MasizPamoja;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SavedMessageRepo {

    private MessageDao messageDao;
    private Executor executor;


    public SavedMessageRepo() {
        executor= Executors.newSingleThreadExecutor();
        messageDao= AppDatabase.getDatabase(MasizPamoja.context).messageDao();
    }

    public void saveMessage(final SavedMessage savedMessage){
        executor.execute(()-> messageDao.saveMessage(savedMessage));
    }



    public void deleteMessage(SavedMessage savedMessage) {
        executor.execute(() -> messageDao.deleteMessage(savedMessage));
    }

    public LiveData<List<SavedMessage>> getAllMessages() {
        return messageDao.getAllMessages();
    }

}
