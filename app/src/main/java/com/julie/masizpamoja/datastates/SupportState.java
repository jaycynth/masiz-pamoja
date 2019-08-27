package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.Support;

public class SupportState {
    private Support support;
    private String message;
    private Throwable errorThrowable;


    public SupportState(Support support) {
        this.support = support;
        this.message = null;
        this.errorThrowable = null;
    }

    public SupportState(String message) {
        this.message = message;
        this.support = null;
        this.errorThrowable = null;
    }

    public SupportState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.support = null;
        this.message = null;

    }

    public Support getSupport() {
        return support;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}
