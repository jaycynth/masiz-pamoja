package com.julie.masizpamoja.api;

import com.julie.masizpamoja.models.AllBlogs;
import com.julie.masizpamoja.models.AllContactList;
import com.julie.masizpamoja.models.AllEvents;
import com.julie.masizpamoja.models.EntryHelpDesk;
import com.julie.masizpamoja.models.ForgotPassword;
import com.julie.masizpamoja.models.GetAllChats;
import com.julie.masizpamoja.models.LatestBlogs;
import com.julie.masizpamoja.models.Login;
import com.julie.masizpamoja.models.Logout;
import com.julie.masizpamoja.models.Register;
import com.julie.masizpamoja.models.SentMessage;
import com.julie.masizpamoja.models.Support;
import com.julie.masizpamoja.models.UpdatePassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgotPassword> forgotPassword(@Field("email") String email);



    @Headers({"Accept: application/json"})
    @PATCH("update_password")
    Call<UpdatePassword> updatePassword(
            @Query("current_password") String currentPassword,
            @Query("new_password") String newPassword,
            @Header("Authorization") String accessToken);

    @Headers({"Accept: application/json"})
    @POST("logout")
    Call<Logout> logout(
            @Header("Authorization") String accessToken
                            );

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

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("events")
    Call<AllEvents> getAllEvents(
            @Header("Authorization") String accessToken
    );

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("help_desk")
    Call<EntryHelpDesk> getHelpDesk(
            @Header("Authorization") String accessToken
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("support")
    Call<Support> support(@Field("name") String name,
                          @Field("email") String email,
                          @Field("message") String message,
                          @Header("Authorization") String accessToken);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("contact_list")
    Call<AllContactList> getAllContactList(
            @Header("Authorization") String accessToken
    );


    @Headers({"Accept: application/json"})
    @GET("messages")
    Call<GetAllChats> getAllMessages(
            @Header("Authorization") String accessToken);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("messages")
    Call<SentMessage> postMessage(
            @Field("message") String message,
            @Header("Authorization") String accessToken);



}
