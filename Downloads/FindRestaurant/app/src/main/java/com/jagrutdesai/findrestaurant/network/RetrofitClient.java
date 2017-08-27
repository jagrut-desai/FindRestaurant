package com.jagrutdesai.findrestaurant.network;

import android.Manifest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jagrut.desai on 8/26/17.
 */

/**
 * This returns a static retrofit client which will be used in app for making network call.
 */
public class RetrofitClient {
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
//     public static final String API_KEY = "AIzaSyBt08WxEypilTzyi2fQBm9OBIgzSt3uk2g";
    public static final String API_KEY = "AIzaSyDqNkkt0w0KxLkH38clUTK_j5mYCXEgV-c";

    public static Retrofit retrofit = null;

    private static Retrofit.Builder builder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL);

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = builder.build();
        }
        return retrofit;
    }
}
