package com.julie.masizpamoja.datastates;

import com.julie.masizpamoja.models.LatestBlogs;

public class LatestBlogsState {
    private LatestBlogs allLatestBlogs;
    private String message;
    private Throwable errorThrowable;


    public LatestBlogsState(LatestBlogs allLatestBlogs) {
        this.allLatestBlogs = allLatestBlogs;
        this.message = null;
        this.errorThrowable = null;
    }

    public LatestBlogsState(String message) {
        this.message = message;
        this.allLatestBlogs = null;
        this.errorThrowable = null;
    }

    public LatestBlogsState(Throwable errorThrowable) {
        this.errorThrowable = errorThrowable;
        this.allLatestBlogs = null;
        this.message = null;

    }

    public LatestBlogs getAllLatestBlogs() {
        return allLatestBlogs;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }

}
