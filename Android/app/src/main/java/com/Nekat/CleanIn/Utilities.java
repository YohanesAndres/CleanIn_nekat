package com.Nekat.CleanIn;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utilities {
    private static final String BASE_URL = "https://cleanid.000webhostapp.com/api/";

    public static Retrofit mRetrofit;

    public static Retrofit getmRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
