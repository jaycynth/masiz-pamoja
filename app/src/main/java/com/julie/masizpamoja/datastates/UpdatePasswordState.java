package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.UpdatePassword;

public class UpdatePasswordState {
    private UpdatePassword updatePassword;
    private String message;
    private Throwable errorThrowable;


    public UpdatePasswordState(UpdatePassword updatePassword) {
        this.updatePassword = updatePassword;
        this.message = null;
        this.errorThrowable = null;
    }

    public UpdatePasswordState(String message) {
        this.message = message;
        this.updatePassword = null;
        this.errorThrowable = null;
    }

    public UpdatePasswordState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.updatePassword = null;
        this.message = null;

    }

    public UpdatePassword getUpdatePassword() {
        return updatePassword;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}
