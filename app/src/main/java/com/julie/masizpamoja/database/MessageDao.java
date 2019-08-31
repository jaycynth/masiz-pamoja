package com.julie.masizpamoja.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.julie.masizpamoja.models.SavedMessage;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MessageDao {

    @Insert(onConflict = REPLACE)
    void saveMessage(SavedMessage savedMessage);

    @Query("SELECT * FROM  SavedMessage")
    LiveData<List<SavedMessage>> getAllMessages();

    @Query("SELECT * FROM SavedMessage")
    List<SavedMessage> getAll();

    @Delete
    void deleteMessage(SavedMessage savedMessage);
}
