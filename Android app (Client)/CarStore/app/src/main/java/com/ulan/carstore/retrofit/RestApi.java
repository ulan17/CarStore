package com.ulan.carstore.retrofit;

import com.google.gson.annotations.SerializedName;
import com.ulan.carstore.model.Car;

import java.util.List;

/**
 * Created by Ulan on 23.04.2019.
 */
public class RestApi {
    @SerializedName("status")
    private String message;

    @SerializedName("data")
    public List<Car> carsList;
}
