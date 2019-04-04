package com.android.goalgeta2.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.goalgeta2.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref_name";
    public static SharedPrefManager myInstance;

    private static final String ID = "id";
    private static final String NAME = "username";
    private static final String EMAIL = "email";
    private static final String PHONE_NO = "phone_number";
    private static final String CREATED = "created_at";
    private static final String UPDATED = "updated_at";
    private static final String TOKEN = "token";


    private Context mCtx;

    public SharedPrefManager(Context context) {
        this.mCtx = context;
    }


    public static synchronized SharedPrefManager getInstance(Context context){

        if (myInstance == null){
            myInstance = new SharedPrefManager(context);
        }
        return myInstance;
    }



    public void userLogin(User user){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (user != null){
            editor.putInt("id", user.getId());
            editor.putString("email", user.getEmail());
            editor.putString("username", user.getName());
            editor.putString("phone_number", user.getPhone_number());
            editor.putString("created_at", user.getCreated_at());
            editor.putString("updated_at", user.getUpdated_at());


            editor.apply();
        }
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(EMAIL, null) != null){
            return true;
        }return false;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(ID, 0),
                sharedPreferences.getString(EMAIL, null),
                sharedPreferences.getString(NAME, null),
                sharedPreferences.getString(PHONE_NO, null),
                sharedPreferences.getString(CREATED, null),
                sharedPreferences.getString(UPDATED, null)
                );
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

        return true;
    }
}
