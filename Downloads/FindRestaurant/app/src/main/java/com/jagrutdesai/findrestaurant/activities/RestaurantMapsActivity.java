package com.jagrutdesai.findrestaurant.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jagrutdesai.findrestaurant.R;
import com.jagrutdesai.findrestaurant.data.RestaurantInfoDetail;
import com.jagrutdesai.findrestaurant.data.Restaurants;
import com.jagrutdesai.findrestaurant.network.RetrofitClient;
import com.jagrutdesai.findrestaurant.network.interfaces.RestaurantApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Show near by restaurant on the map. Using GoogleMap Fragment.
 */
public class RestaurantMapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private static final int DEFAULT_ZOOM = 11;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private Retrofit retrofitClient;
    private List<Restaurants.Restaurant> restaurantList;
    private String location;

    /**
     * Get the views ready and inflate google map fragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        retrofitClient = RetrofitClient.getRetrofit();
        //get location from extras
        location = getIntent().getExtras().getString("location");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNearByRestaurants();
    }

    /**
     * Get near by restaurant from location passed in by previous activity.
     */
    public void getNearByRestaurants() {
        RestaurantApiInterface restaurantApiInterface = retrofitClient.create(RestaurantApiInterface.class);
        final Call<Restaurants> restaurantsCall = restaurantApiInterface.getRestaurants(location, RetrofitClient.API_KEY, "restaurant", "5000");
        restaurantsCall.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                restaurantList = response.body().getRestaurantList();
                for (int index = 0; index < restaurantList.size(); index++) {
                    LatLng placePosition = new LatLng(restaurantList.get(index).getGeometry().getLocation().getLatitude(),
                            restaurantList.get(index).getGeometry().getLocation().getLongitude());
                    final Marker marker = mMap.addMarker(new MarkerOptions().position(placePosition).title(restaurantList.get(index).getName()));
                    marker.setTag(restaurantList.get(index));
                }

                String[] locationSplit = location.split(",");
                LatLng userPosition = new LatLng(Double.parseDouble(locationSplit[0]), Double.parseDouble(locationSplit[1]));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, DEFAULT_ZOOM));
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Toast.makeText(RestaurantMapsActivity.this, "No Network Connection", Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setInfoWindowAdapter(infoWindowAdapter);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * Onclick to any marker go to detail activity.
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("placeId",((Restaurants.Restaurant)marker.getTag()).getPlaceId());
        startActivity(intent);
        return true;
    }
}
