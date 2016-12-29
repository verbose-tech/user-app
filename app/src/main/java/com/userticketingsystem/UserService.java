package com.userticketingsystem;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mayank on 29/12/16.
 */

public interface UserService {

    @FormUrlEncoded
    @POST("/api/bus/passenger/")
    Call<UserResponse> signUp(@Field("email") String email, @Field("name") String name, @Field("profile_pic") String profilePic);
}
