package com.julie.masizpamoja.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.julie.masizpamoja.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.julie.masizpamoja.api.ApiEndpoints.BASE_URL;

public class ApiClient {

    private Retrofit retrofit = null;
    private Context context;

    //Configure OkHttpClient
    private OkHttpClient.Builder okHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(3, TimeUnit.MINUTES);
        okHttpClient.readTimeout(3,TimeUnit.MINUTES);
        okHttpClient.writeTimeout(3,TimeUnit.MINUTES);
//        okHttpClient.addInterceptor(chain -> {
//            Request request = chain.request();
//            Response response = chain.proceed(request);
//
//            //re-direct user to login
//            if (response.code() == 401 || response.code() == 500) {
//
//                Intent logoutIntent = new Intent(context, LoginActivity.class);
//                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(logoutIntent);
//
//                return response;
//            }
//            return response;
//        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(logging);
        }

        return okHttpClient;
    }

    public ApiClient(Context context){
        this.context = context;
    }


    public Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        OkHttpClient.Builder okhttpBuilder = okHttpClient();
        builder.client(okhttpBuilder.build());

        retrofit = builder.build();

        return retrofit;
    }

    /**
     * Create handle for the RetrofitInstance interface
     */
    public ApiService mApiservice() {
        return getClient().create(ApiService.class);
    }
}
