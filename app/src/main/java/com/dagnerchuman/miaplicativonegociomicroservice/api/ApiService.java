package com.dagnerchuman.miaplicativonegociomicroservice.api;

import com.dagnerchuman.miaplicativonegociomicroservice.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    String baseUser = "api/authentication";
    @POST(baseUser + "/sign-in")
    Call<User> signIn(@Body User user);

    @Headers("Authorization: Bearer")
    @GET("api/user")
    Call<User> getCurrentUser(@Body User user);


    @POST(baseUser + "/sign-up")
    Call<User> signUp(@Body User user);

}
