package com.ulan.carstore.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ulan on 22.04.2019.
 */
public interface GetDataService {
    @GET("/cars")
    Call<RestApi> getAllCars();
}
