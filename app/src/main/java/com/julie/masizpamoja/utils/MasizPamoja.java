package com.julie.masizpamoja.utils;

import android.app.Application;
import android.content.Context;


public class MasizPamoja extends Application {

    public static Context context;

    private static MasizPamoja mInstance;

//    private Socket mSocket;
//    {
//        try {
//            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Socket getSocket() {
//        return mSocket;
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        mInstance = this;

    }


}
