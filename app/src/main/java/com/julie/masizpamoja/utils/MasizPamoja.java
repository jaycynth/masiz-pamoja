package com.julie.masizpamoja.utils;

import android.app.Application;
import android.content.Context;


public class MasizPamoja extends Application {

    public static Context context;

    private static MasizPamoja mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        mInstance = this;

    }
}
