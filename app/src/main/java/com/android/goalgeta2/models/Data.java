package com.android.goalgeta2.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {

    @SerializedName("user")
        User UserObject;
        @SerializedName("goals")
        ArrayList<Goal> goals = new ArrayList <Goal> ();
    @SerializedName("success")
        private boolean success;
    @SerializedName("message")
        private String message;
    @SerializedName("token")
        private String token;


        // Getter Methods

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    public ArrayList<Goal> getGoals() {
        return goals;
    }



    public void setUserObject(User userObject) {
        UserObject = userObject;
    }

    // Setter Methods

        public User getUser() {
            return UserObject;
        }

        public void setUser(User userObject) {
            this.UserObject = userObject;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }


        public void setGoals(ArrayList<Goal> goals) {
            this.goals = goals;
        }
}
