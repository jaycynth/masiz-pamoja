package com.julie.masizpamoja.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllContactList {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("contact_list")
    @Expose
    private List<ContactList> contactList = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<ContactList> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactList> contactList) {
        this.contactList = contactList;
    }
}
