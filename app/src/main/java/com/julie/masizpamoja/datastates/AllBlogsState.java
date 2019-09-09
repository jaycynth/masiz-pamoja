package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.AllBlogs;

public class AllBlogsState {
    private AllBlogs allBlogs;
    private String message;
    private Throwable errorThrowable;


    public AllBlogsState(AllBlogs allBlogs) {
        this.allBlogs = allBlogs;
        this.message = null;
        this.errorThrowable = null;
    }

    public AllBlogsState(String message) {
        this.message = message;
        this.allBlogs = null;
        this.errorThrowable = null;
    }

    public AllBlogsState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allBlogs = null;
        this.message = null;

    }

    public AllBlogs getAllBlogs() {
        return allBlogs;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }
}