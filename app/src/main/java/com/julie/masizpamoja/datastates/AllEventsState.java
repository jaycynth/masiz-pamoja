package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.AllEvents;

public class AllEventsState {
    private AllEvents allEvents;
    private String message;
    private Throwable errorThrowable;


    public AllEventsState(AllEvents allEvents) {
        this.allEvents = allEvents;
        this.message = null;
        this.errorThrowable = null;
    }

    public AllEventsState(String message) {
        this.message = message;
        this.allEvents = null;
        this.errorThrowable = null;
    }

    public AllEventsState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allEvents = null;
        this.message = null;

    }

    public AllEvents getAllEvents() {
        return allEvents;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}