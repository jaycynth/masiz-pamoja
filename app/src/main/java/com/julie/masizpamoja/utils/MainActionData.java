package com.julie.masizpamoja.utils;

import com.julie.masizpamoja.R;
import com.julie.masizpamoja.models.MainAction;

import java.util.ArrayList;
import java.util.List;

public class MainActionData {
    public static List<MainAction> getMainAction(){
        List<MainAction> actionList = new ArrayList<>();

        MainAction blog = new MainAction(R.drawable.ic_blog_comment_speech_bubble_symbol,"Blog");
        actionList.add(blog);

        MainAction blog1 = new MainAction(R.drawable.ic_calendar,"Upcoming Events");
        actionList.add(blog1);

        MainAction blog2 = new MainAction(R.drawable.ic_content,"More Info");
        actionList.add(blog2);

        MainAction blog3= new MainAction(R.drawable.ic_help,"Need Help");
        actionList.add(blog3);

        MainAction blog4= new MainAction(R.drawable.ic_iconfinder_06_111027,"Chat Room");
        actionList.add(blog4);


        return actionList;
    }
}
