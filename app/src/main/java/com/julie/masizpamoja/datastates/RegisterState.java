package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.NotFound;
import com.julie.masizpamoja.models.Register;
import com.julie.masizpamoja.models.RegisterUnprocessableEntity;


public class RegisterState {
    private Register allNewUsers;
    private String message;
    private Throwable errorThrowable;
    private RegisterUnprocessableEntity registerUnprocessableEntity;
    private NotFound notFound;


    public RegisterState(Register allNewUsers) {
        this.allNewUsers = allNewUsers;
        this.message = null;
        this.errorThrowable = null;
        this.registerUnprocessableEntity = null;
        this.notFound = null;

    }

    public RegisterState(String message) {
        this.message = message;
        this.allNewUsers = null;
        this.errorThrowable =null;
        this.registerUnprocessableEntity = null;
        this.notFound = null;

    }

    public RegisterState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allNewUsers = null;
        this.message = null;
        this.registerUnprocessableEntity = null;
        this.notFound = null;


    }

    public RegisterState(RegisterUnprocessableEntity registerUnprocessableEntity) {
        this.registerUnprocessableEntity = registerUnprocessableEntity;
        this.errorThrowable = null;
        this.allNewUsers = null;
        this.message = null;

        this.notFound = null;

    }

    public RegisterState(NotFound notFound) {
        this.notFound = notFound;
        this.registerUnprocessableEntity = null;
        this.errorThrowable = null;
        this.allNewUsers = null;
        this.message = null;
    }

    public NotFound getNotFound() {
        return notFound;
    }

    public RegisterUnprocessableEntity getRegisterUnprocessableEntity() {
        return registerUnprocessableEntity;
    }

    public Register getAllNewUsers() {
        return allNewUsers;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }


}