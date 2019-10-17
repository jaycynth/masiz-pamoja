package com.julie.masizpamoja.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllChats {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("chats")
    @Expose
    private List<Chat> chats = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

}

