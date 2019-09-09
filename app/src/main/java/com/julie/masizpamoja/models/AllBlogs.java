package com.julie.masizpamoja.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllBlogs {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("blogs")
    @Expose

    private List<Blog> blogs = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}