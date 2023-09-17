package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    String baseUser = "api/authentication";
    @POST(baseUser + "/sign-in")
    Call<User> signIn(@Body User user);

    @GET("api/user")
    Call<User> getCurrentUser(@Header("Authorization") String token);


    @POST(baseUser + "/sign-up")
    Call<User> signUp(@Body User user);

}
