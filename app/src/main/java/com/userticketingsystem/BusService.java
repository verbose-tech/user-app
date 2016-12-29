package com.userticketingsystem;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mayank on 29/12/16.
 */

public interface BusService {

    @FormUrlEncoded
    @PUT("/api/bus/passenger/{id}/")
    Call<ApiResponse> checkIn(@Path("id") Integer id, @Field("wallet") Float wallet, @Field("boarding_flag") Boolean boardingFlag);
}
