package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.GetAllChats;

public class GetAllChatsState {

    private GetAllChats getAllChats;
    private String message;
    private Throwable throwable;

    public GetAllChatsState(GetAllChats getAllChats) {
        this.getAllChats = getAllChats;
        this.message = null;
        this.throwable = null;
    }

    public GetAllChatsState(String message) {
        this.message = message;
        this.getAllChats = null;
        this.throwable = null;
    }

    public GetAllChatsState(Throwable throwable) {
        this.throwable = throwable;
        this.message = null;
        this.getAllChats = null;
    }

    public GetAllChats getGetAllChats() {
        return getAllChats;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
