package com.android.goalgeta2.models;

import com.google.gson.annotations.SerializedName;

public class GoalResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
        private String data;

    @SerializedName("goal")
        Goal GoalObject;

    @SerializedName("token")
    private String token;


        // Getter Methods

        public boolean getStatus() {
            return status;
        }

        public String getData() {
            return data;
        }

        public Goal getGoal() {
            return GoalObject;
        }

        public String getToken() {
        return token;
    }

        // Setter Methods

        public void setStatus(boolean status) {
            this.status = status;
        }

        public void setData(String data) {
            this.data = data;
        }

        public void setGoal(Goal goalObject) {
            this.GoalObject = goalObject;
        }
        public void setToken(String token) {
        this.token = token;
    }

    public class Goal {
        private float user_id;
        private String title;
        private String description;
        private String completed;
        private String start;
        private String finish;
        private String updated_at;
        private String created_at;
        private float id;


        // Getter Methods

        public float getUser_id() {
            return user_id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getCompleted() {
            return completed;
        }

        public String getStart() {
            return start;
        }

        public String getFinish() {
            return finish;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public float getId() {
            return id;
        }

        // Setter Methods

        public void setUser_id(float user_id) {
            this.user_id = user_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCompleted(String completed) {
            this.completed = completed;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public void setFinish(String finish) {
            this.finish = finish;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(float id) {
            this.id = id;
        }
    }
}
