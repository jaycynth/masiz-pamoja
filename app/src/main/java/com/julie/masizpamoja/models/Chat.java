package com.julie.masizpamoja.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("messsage")
    @Expose
    private Messsage messsage;

    public Messsage getMesssage() {
        return messsage;
    }

    public void setMesssage(Messsage messsage) {
        this.messsage = messsage;
    }
}
