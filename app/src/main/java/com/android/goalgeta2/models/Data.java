package com.android.goalgeta2.models;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("success")
    private boolean success;
    @SerializedName("user")
    User UserObject;
    @SerializedName("token")
    private String token;


    // Getter Methods

    public boolean getSuccess() {
        return success;
    }

    public User getUser() {
        return UserObject;
    }

    public String getToken() {
        return token;
    }

    // Setter Methods

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setUser(User userObject) {
        this.UserObject = userObject;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
