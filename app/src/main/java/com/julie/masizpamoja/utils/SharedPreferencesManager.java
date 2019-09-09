package com.julie.masizpamoja.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

  public static final String SHARED_PREF_NAME = "prefs";
  private static SharedPreferencesManager mInstance;
  private Context context;

  public SharedPreferencesManager(Context context) {
    this.context = context;
  }

  public static synchronized SharedPreferencesManager getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new SharedPreferencesManager(context);
    }
    return mInstance;
  }




  public void saveUserImage(String filePath) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.PROFILE_IMAGE, filePath);
    editor.apply();

  }
  public void saveToken(String token) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.TOKEN, token);
    editor.apply();

  }
  public void saveNames(String names) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.FULL_NAMES, names);
    editor.apply();

  }
  public void saveOccupation(String occupation) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.OCCUPATION, occupation);
    editor.apply();

  }
  public void saveEmail(String email) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.EMAIL, email);
    editor.apply();

  }

  public void saveUserId(String uniqueId) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.UNIQUEID, uniqueId);
    editor.apply();

  }

  public String getUserImage() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Constants.PROFILE_IMAGE, null);

  }
  public String getToken() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Constants.TOKEN, null);

  }
  public String getNames() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Constants.FULL_NAMES, null);

  }
  public String getOccuption() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Constants.OCCUPATION, null);

  }
  public String getEmail() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Constants.EMAIL, null);

  }

  public String getUniqueid() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString(Constants.UNIQUEID, null);

  }

  //check whether a user is logged in
  public boolean isLoggedIn() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    return sharedPreferences.getString("token", null) != null;
  }

  //for logout
  public void clear() {
    SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear();
    editor.apply();


  }



}