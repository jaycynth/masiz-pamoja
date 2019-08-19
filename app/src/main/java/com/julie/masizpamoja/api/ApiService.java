package com.julie.masizpamoja.api;

import com.julie.masizpamoja.models.AllBlogs;
import com.julie.masizpamoja.models.LatestBlogs;
import com.julie.masizpamoja.models.Login;
import com.julie.masizpamoja.models.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("login")
    Call<Login> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("register")
    Call<Register> createUser(@Field("email") String email,
                              @Field("password") String password,
                              @Field("name") String name);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("blogs")
    Call<AllBlogs> getAllBlogs(
            @Header("Authorization") String accessToken
    );
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("latestblogs")
    Call<LatestBlogs> getLatestBlogs(
            @Header("Authorization") String accessToken
    );

}
