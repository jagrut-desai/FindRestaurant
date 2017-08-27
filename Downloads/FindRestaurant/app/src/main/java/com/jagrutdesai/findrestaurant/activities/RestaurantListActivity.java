package com.jagrutdesai.findrestaurant.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jagrutdesai.findrestaurant.R;
import com.jagrutdesai.findrestaurant.adapters.RestaurantListAdapter;
import com.jagrutdesai.findrestaurant.data.RestaurantInfoDetail;
import com.jagrutdesai.findrestaurant.data.Restaurants;
import com.jagrutdesai.findrestaurant.network.RetrofitClient;
import com.jagrutdesai.findrestaurant.network.interfaces.RestaurantApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Show near by restaurants in list views.
 */
public class RestaurantListActivity extends AppCompatActivity {

    private ListView listView;
    private RestaurantListAdapter restaurantListAdapter;
    private Retrofit retrofitClient;
    private List<Restaurants.Restaurant> restaurantList;
    private String location;
    private String placeId;
    private RestaurantInfoDetail.RestaurantDetail restaurantDetail;


    /**
     * Get the views and onclick listners for list of restaurants.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        location = getIntent().getExtras().getString("location");
        listView = (ListView) findViewById(R.id.restaurant_list);
        retrofitClient = RetrofitClient.getRetrofit();

        //on particular item clicked got to detail activity.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RestaurantListActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("placeId",restaurantList.get(i).getPlaceId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNearByRestaurants();
    }

    /**
     * Get near by restaurants.
     */
    public void getNearByRestaurants() {
        RestaurantApiInterface restaurantApiInterface = retrofitClient.create(RestaurantApiInterface.class);
        final Call<Restaurants> restaurantsCall = restaurantApiInterface.getRestaurants(location, RetrofitClient.API_KEY, "restaurant", "5000");
        restaurantsCall.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                restaurantList = response.body().getRestaurantList();
                restaurantListAdapter = new RestaurantListAdapter(RestaurantListActivity.this,R.layout.restaurant_list_item, restaurantList);
                listView.setAdapter(restaurantListAdapter);
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Toast.makeText(RestaurantListActivity.this, "No Network Connection", Toast.LENGTH_LONG);
            }
        });
    }
}
