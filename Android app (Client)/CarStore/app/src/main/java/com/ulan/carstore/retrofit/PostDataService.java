package com.ulan.carstore.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Ulan on 13.05.2019.
 */
public interface PostDataService {
    @Headers({
            "Content-Type:application/json"
    })
    @POST("/cars")
    Call<RestApi> sendCar(@Body Map<String, String> params);
}
