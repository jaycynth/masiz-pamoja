package com.julie.masizpamoja.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.julie.masizpamoja.BuildConfig;
import com.julie.masizpamoja.api.ApiClient;
import com.julie.masizpamoja.datastates.HelpDeskState;
import com.julie.masizpamoja.models.EntryHelpDesk;
import com.julie.masizpamoja.models.HelpDesk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelpDeskRepo {

  private ApiClient mApiClient;

  //constructor
  public HelpDeskRepo(Application application) {
    mApiClient = new ApiClient(application);

  }

  public LiveData<HelpDeskState> helpDesk(String accessToken) {

    MutableLiveData<HelpDeskState> helpDeskStateMutableLiveData = new MutableLiveData<>();
    Call<EntryHelpDesk> call = mApiClient.mApiservice().getHelpDesk(accessToken);
    call.enqueue(new Callback<EntryHelpDesk>() {
      @Override
      public void onResponse(Call<EntryHelpDesk> call, Response<EntryHelpDesk> response) {
        if (response.code() == 200) {
          helpDeskStateMutableLiveData.setValue(new HelpDeskState(response.body()));

        } else {
          if (BuildConfig.DEBUG){
            helpDeskStateMutableLiveData.setValue(new HelpDeskState(response.message()));

          }else {
            helpDeskStateMutableLiveData.setValue(new HelpDeskState("An error occurred"));
          }

        }
      }

      @Override
      public void onFailure(Call<EntryHelpDesk> call, Throwable t) {
        helpDeskStateMutableLiveData.setValue(new HelpDeskState(t));
      }
    });

    return helpDeskStateMutableLiveData;

  }
}