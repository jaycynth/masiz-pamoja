package com.julie.masizpamoja.utils;

import com.julie.masizpamoja.models.MainAction;

import java.util.ArrayList;
import java.util.List;

public class MainActionData {
    public static List<MainAction> getMainAction(){
        List<MainAction> actionList = new ArrayList<>();

        MainAction blog = new MainAction("Blogs");
        actionList.add(blog);

        MainAction blog1 = new MainAction("Upcoming Events");
        actionList.add(blog1);

        MainAction blog2 = new MainAction("Need Help");
        actionList.add(blog2);

        MainAction blog3= new MainAction("Chat Room");
        actionList.add(blog3);


        return actionList;
    }
}
