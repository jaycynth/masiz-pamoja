package com.julie.masizpamoja.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedMessage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String UniqueId;

    private String Username;
    private String Message;


    public SavedMessage() {
    }

    public SavedMessage(String uniqueId, String username, String message) {
        UniqueId = uniqueId;
        Username = username;
        Message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
