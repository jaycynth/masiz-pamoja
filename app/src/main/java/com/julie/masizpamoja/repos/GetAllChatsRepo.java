package com.julie.masizpamoja.repos;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.GetAllChatsState;
import com.julie.masizpamoja.models.GetAllChats;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllChatsRepo {

    private ApiClient apiClient;

    public GetAllChatsRepo(Context context) {
        apiClient = new ApiClient(context);
    }

    public LiveData<GetAllChatsState> getAllChats(String accessToken){

        MutableLiveData<GetAllChatsState> getAllChatsStateMutableLiveData = new MutableLiveData<>();
         Call<GetAllChats> call = apiClient.mApiservice().getAllMessages(accessToken);

         call.enqueue(new Callback<GetAllChats>() {
             @Override
             public void onResponse(Call<GetAllChats> call, Response<GetAllChats> response) {

                 if(response.code() == 200){

                     getAllChatsStateMutableLiveData.setValue(new GetAllChatsState(response.body()));
                 }else{
                     if(BuildConfig.DEBUG) {
                         getAllChatsStateMutableLiveData.setValue(new GetAllChatsState(response.message()));
                     }else{
                         getAllChatsStateMutableLiveData.setValue(new GetAllChatsState("An Error Occurred"));
                     }
                 }

             }

             @Override
             public void onFailure(Call<GetAllChats> call, Throwable t) {
                   getAllChatsStateMutableLiveData.setValue(new GetAllChatsState(t));
             }
         });

        return getAllChatsStateMutableLiveData;
    }
}
