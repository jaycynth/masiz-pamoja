package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.Logout;


public class LogoutState {
    private Logout allLogouts;
    private String message;
    private Throwable errorThrowable;


    public LogoutState(Logout allLogouts) {
        this.allLogouts = allLogouts;
        this.message =null;
        this.errorThrowable = null;
    }

    public LogoutState(String message) {
        this.message = message;
        this.allLogouts = null;
        this.errorThrowable =null;
    }

    public LogoutState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allLogouts = null;
        this.message = null;

    }



    public Logout getAllLogouts() {
        return allLogouts;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }

}
