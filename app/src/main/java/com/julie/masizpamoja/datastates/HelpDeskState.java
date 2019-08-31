package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.EntryHelpDesk;
import com.julie.masizpamoja.models.HelpDesk;

public class HelpDeskState {
    private EntryHelpDesk helpDesk;
    private String message;
    private Throwable errorThrowable;


    public HelpDeskState(EntryHelpDesk helpDesk) {
        this.helpDesk = helpDesk;
        this.message = null;
        this.errorThrowable = null;
    }

    public HelpDeskState(String message) {
        this.message = message;
        this.helpDesk = null;
        this.errorThrowable = null;
    }

    public HelpDeskState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.helpDesk = null;
        this.message = null;

    }

    public EntryHelpDesk getHelpDesk() {
        return helpDesk;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}