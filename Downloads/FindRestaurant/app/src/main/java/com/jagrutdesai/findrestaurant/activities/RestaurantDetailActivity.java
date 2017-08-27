package com.jagrutdesai.findrestaurant.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jagrutdesai.findrestaurant.R;
import com.jagrutdesai.findrestaurant.data.RestaurantInfoDetail;
import com.jagrutdesai.findrestaurant.network.RetrofitClient;
import com.jagrutdesai.findrestaurant.network.interfaces.RestaurantApiInterface;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Details about the restaurant will be shown here.
 */
public class RestaurantDetailActivity extends AppCompatActivity {

    private ImageView image;
    private TextView title;
    private TextView address;
    private TextView phoneNumber;
    private TextView rating;
    private TextView review;
    private String placeId;
    private Retrofit retrofitClient;
    private RestaurantInfoDetail.RestaurantDetail restaurantDetail;


    /**
     * Create all the views for displaying details of a particular restaurant.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        retrofitClient = RetrofitClient.getRetrofit();
        placeId = getIntent().getExtras().getString("placeId");
        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        address = (TextView) findViewById(R.id.address);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        rating = (TextView) findViewById(R.id.rating);
        review = (TextView) findViewById(R.id.review);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRestaurntDetail();
    }

    /**
     * Get Restaurant detail from place Id which was passed from previous activity,
     */
    public void getRestaurntDetail(){

        RestaurantApiInterface restaurantApiInterface = retrofitClient.create(RestaurantApiInterface.class);
        final Call<RestaurantInfoDetail> restaurantDetailCall =
                restaurantApiInterface.getRestaurantDetail(placeId, RetrofitClient.API_KEY);
        restaurantDetailCall.enqueue(new Callback<RestaurantInfoDetail>() {
            @Override
            public void onResponse(Call<RestaurantInfoDetail> call, Response<RestaurantInfoDetail> response) {
                restaurantDetail = response.body().getRestaurantDetail();
                title.setText(restaurantDetail.getName());
                address.setText(restaurantDetail.getAddress());
                phoneNumber.setText(restaurantDetail.getPhoneNumber());
                rating.setText(restaurantDetail.getRating()+" Stars");
                review.setText(restaurantDetail.getReviews().get(0).getReviewText());
                getRestaurantPhoto();
            }

            @Override
            public void onFailure(Call<RestaurantInfoDetail> call, Throwable t) {

            }
        });
    }

    /**
     * Get photo for the restaurant.
     */
    public void getRestaurantPhoto(){
        RestaurantApiInterface restaurantApiInterface = retrofitClient.create(RestaurantApiInterface.class);
        String photoReference = restaurantDetail.getPhotos().get(0).getPhotoReference();
        final Call<ResponseBody> restaurantDetailCall = restaurantApiInterface.getRestaurantPhoto(photoReference,RetrofitClient.API_KEY, "700");
        restaurantDetailCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Picasso.with(RestaurantDetailActivity.this).load(response.raw().request().url().toString()).into(image);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
