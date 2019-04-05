package com.android.goalgeta2.models;

public class User {
    private  int id;
    private String name;
    private String email;
    private String phone_number;
    private String created_at;
    private String updated_at;
    private String token;

    public User(int id, String name, String email, String phone_number, String created_at, String updated_at,String token) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.token = token;
    }
    // Getter Methods

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getToken(){
        return token;
    }
    // Setter Methods

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setToken(String token){
        this.token = token;
    }
}
