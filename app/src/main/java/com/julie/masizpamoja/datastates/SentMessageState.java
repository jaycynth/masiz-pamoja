package com.julie.masizpamoja.datastates;


import com.julie.masizpamoja.models.SentMessage;

public class SentMessageState {

    private SentMessage sentMessage;
    private String message;
    private Throwable errorThrowable;

    public SentMessageState(SentMessage sentMessage) {
        this.sentMessage = sentMessage;
        this.message = null;
        this.errorThrowable = null;
    }

    public SentMessageState(String message) {
        this.message = message;
        this.sentMessage = null;
        this.errorThrowable = null;
    }

    public SentMessageState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.sentMessage = null;
        this.message = null;
    }

    public SentMessage getSentMessage() {
        return sentMessage;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}
