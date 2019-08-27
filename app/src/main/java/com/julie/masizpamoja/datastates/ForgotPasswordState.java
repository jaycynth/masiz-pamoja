package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.ForgotPassword;

public class ForgotPasswordState {
    private ForgotPassword forgotPassword;
    private String message;
    private Throwable errorThrowable;


    public ForgotPasswordState(ForgotPassword forgotPassword) {
        this.forgotPassword = forgotPassword;
        this.message = null;
        this.errorThrowable = null;
    }

    public ForgotPasswordState(String message) {
        this.message = message;
        this.forgotPassword = null;
        this.errorThrowable = null;
    }

    public ForgotPasswordState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.forgotPassword = null;
        this.message = null;

    }

    public ForgotPassword getForgotPassword() {
        return forgotPassword;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}
