package com.julie.masizpamoja.utils;

import com.julie.masizpamoja.models.RandomBlogs;

import java.util.ArrayList;
import java.util.List;

public class RandomBlogsData {
    public static List<RandomBlogs> getRandomBlogs() {
        List<RandomBlogs> randomBlogsList = new ArrayList<>();

        RandomBlogs blog = new RandomBlogs("Julie Kivuva", "Courtesy for a feeling");
        randomBlogsList.add(blog);

        RandomBlogs blog1 = new RandomBlogs("Eric Ndung'u", "Designing the aftermath");
        randomBlogsList.add(blog1);


        return randomBlogsList;
    }
}
