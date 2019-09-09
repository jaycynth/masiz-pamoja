package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.Login;
import com.julie.masizpamoja.models.LoginError;


public class LoginState
{
    private Login allLogins;
    private String message;
    private Throwable errorThrowable;
    private LoginError loginError;


    public LoginState(Login allLogins) {
        this.allLogins = allLogins;
        this.message =null;
        this.errorThrowable = null;
        this.loginError = null;
    }

    public LoginState(String message) {
        this.message = message;
        this.allLogins = null;
        this.errorThrowable =null;
        this.loginError = null;
    }

    public LoginState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allLogins = null;
        this.message = null;
        this.loginError = null;

    }

    public LoginState(LoginError loginError) {
        this.loginError = loginError;
        this.errorThrowable = null;
        this.allLogins = null;
        this.message = null;

    }


    public Login getAllLogins() {
        return allLogins;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }

    public LoginError getLoginError() {
        return loginError;
    }
}