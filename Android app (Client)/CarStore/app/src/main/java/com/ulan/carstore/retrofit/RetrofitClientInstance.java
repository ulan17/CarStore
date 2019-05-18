package com.ulan.carstore.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ulan on 22.04.2019.
 */

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    // URL to get data. Although I use local server, you can use global server;
    private static final String BASE_URL = "http://1.1.1.1:8080";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}