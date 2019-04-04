package com.android.goalgeta2.models;

import com.google.gson.annotations.SerializedName;

public class Goal {
    @SerializedName("user_id")
    private float user_id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("completed")
    private String completed;

    @SerializedName("start")
    private String start;

    @SerializedName("finish")
    private String finish;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("id")
    private float id;



    /**
     * Constructs an empty list with the specified initial capacity.
     *
     *
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public Goal(String title, String description, String completed, String start) {

        this.title = title;
        this.description = description;
        this.completed = completed;
        this.start = start;
    }

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

