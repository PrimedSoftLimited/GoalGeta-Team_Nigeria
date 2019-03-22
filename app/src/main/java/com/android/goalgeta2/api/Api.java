package com.android.goalgeta2.api;

import com.android.goalgeta2.models.GoalResponse;
import com.android.goalgeta2.models.ResponseObb;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseObb> register(
            @Field("name") String username,
            @Field("email") String email,
            @Field("phone_number") String phoneNo,
            @Field("password") String password,
            @Field("password_confirmation") String cnfPassword

    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseObb>login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("goals")
    Call<GoalResponse>goals(
            @Header("Authorization") String token,
            @Field("title") String title,
            @Field("description") String description,
            @Field("completed") String completed,
            @Field("start") String start,
            @Field("finish") String finish
    );

    @GET("profile")
    Call<ResponseObb>profile(@Header("Authorization") String authToken);

}
