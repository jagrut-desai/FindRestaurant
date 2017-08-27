package com.jagrutdesai.findrestaurant.network.interfaces;

import com.jagrutdesai.findrestaurant.data.RestaurantInfoDetail;
import com.jagrutdesai.findrestaurant.data.Restaurants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jagrut.desai on 8/26/17.
 */

// Client interface to make network call.
public interface RestaurantApiInterface {

    //Get for Restaurants top level info
    @GET("nearbysearch/json")
    Call<Restaurants> getRestaurants(@Query("location") String latLng, @Query("key") String apiKey, @Query("type") String type, @Query("radius") String radius);

    //Get details about each restaurant.
    @GET("details/json")
    Call<RestaurantInfoDetail> getRestaurantDetail(@Query("placeid") String placeID, @Query("key") String apiKey);

    //Get photo of the restaurant.
    @GET("photo")
    Call<ResponseBody> getRestaurantPhoto(@Query("photoreference") String photoReference, @Query("key") String apiKey, @Query("maxwidth") String maxWidth);


}
