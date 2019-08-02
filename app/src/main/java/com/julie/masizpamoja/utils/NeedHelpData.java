package com.julie.masizpamoja.utils;

import com.julie.masizpamoja.models.NeedHelp;
import com.julie.masizpamoja.models.RandomBlogs;

import java.util.ArrayList;
import java.util.List;

public class NeedHelpData {
    public static List<NeedHelp> needHelpDatas() {
        List<NeedHelp> needHelpList = new ArrayList<>();

        NeedHelp blog = new NeedHelp("GBV");
        needHelpList.add(blog);

        NeedHelp blog1 = new NeedHelp("Sexual Health");
        needHelpList.add(blog1);

        NeedHelp blog2 = new NeedHelp("HIV/AIDS");
        needHelpList.add(blog2);

        NeedHelp blog3 = new NeedHelp("Rape");
        needHelpList.add(blog3);

        NeedHelp blog4 = new NeedHelp("Abortion");
        needHelpList.add(blog4);

        NeedHelp blog5 = new NeedHelp("Gynocare");
        needHelpList.add(blog5);

        NeedHelp blog6 = new NeedHelp("Teenage Pregnancies");
        needHelpList.add(blog6);







        return needHelpList;
    }
}
