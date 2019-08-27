package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.AllContactList;

public class AllContactListState {
    private AllContactList allContactList;
    private String message;
    private Throwable errorThrowable;


    public AllContactListState(AllContactList allContactList) {
        this.allContactList = allContactList;
        this.message = null;
        this.errorThrowable = null;
    }

    public AllContactListState(String message) {
        this.message = message;
        this.allContactList = null;
        this.errorThrowable = null;
    }

    public AllContactListState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allContactList = null;
        this.message = null;

    }

    public AllContactList getAllContactList() {
        return allContactList;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}
