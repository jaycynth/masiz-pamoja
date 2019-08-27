package com.julie.masizpamoja.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EntryHelpDesk {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("help_desk")
    @Expose
    private List<HelpDesk> helpDesk = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<HelpDesk> getHelpDesk() {
        return helpDesk;
    }

    public void setHelpDesk(List<HelpDesk> helpDesk) {
        this.helpDesk = helpDesk;
    }
}
