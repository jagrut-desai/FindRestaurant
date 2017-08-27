package com.jagrutdesai.findrestaurant.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.jagrutdesai.findrestaurant.R;

import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Choose how you want to find the restaurant near you.
 */
public class MainActivity extends AppCompatActivity implements LocationListener {

    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int DEFAULT_ZOOM = 11;
    private boolean locationLocked = false;
    private LocationManager locationManager;
    private String provider;
    private Location location;

    /**
     * Create views and set onclick listner for activities.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = GPS_PROVIDER;

        Button mapActivity = (Button) findViewById(R.id.restaurant_map_activity);
        Button listActivity = (Button) findViewById(R.id.restaurant_list_activity);

        mapActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(MainActivity.this, RestaurantMapsActivity.class);
                //pass the location along
                activity.putExtra("location", location.getLatitude()+","+location.getLongitude());
                startActivity(activity);
            }
        });

        listActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(MainActivity.this, RestaurantListActivity.class);
                //pass the location along
                activity.putExtra("location", location.getLatitude()+","+location.getLongitude());
                startActivity(activity);
            }
        });
    }

    /**
     * Check for the permision of location on resume of activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                canGetLocation();
                //Request location updates:
                locationManager.requestLocationUpdates(provider, 400, 1, this);
            }
        }
    }

    /**
     * Check location permission.
     * @return
     */
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show alert dialog for explanation of the location.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                        PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                //request permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Result of user accepting goes here.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // chekcing for permission granted by user
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        // request location updates
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {
                    // we do not have permission.
                }
                return;
            }

        }
    }

    /**
     * On location update change our app location.
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    /**
     * Check if GPS is turned on. Keep chekcing until you do.
     */
    public void canGetLocation() {
        boolean gps_enabled;
        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        gps_enabled = locationManager.isProviderEnabled(GPS_PROVIDER);

        if (!gps_enabled) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.turn_on_gps)
                    .setMessage(R.string.text_location_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            canGetLocation();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        // do nothing.
    }

    @Override
    public void onProviderEnabled(String s) {
        // do nothing.
    }

    @Override
    public void onProviderDisabled(String s) {
        // do nothing.
    }
}
