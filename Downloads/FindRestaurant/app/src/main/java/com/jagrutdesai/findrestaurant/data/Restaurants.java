package com.jagrutdesai.findrestaurant.data;

import android.support.annotation.StringRes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jagrut.desai on 8/26/17.
 */

/**
 * Basic Restaurant info to show on map/list acitivities.
 */
public class Restaurants implements Serializable {
    @SerializedName("results")
    List<Restaurant> restaurantList;

    public class Restaurant implements Serializable {
        @SerializedName("geometry")
        Geometry geometry;
        @SerializedName("icon")
        String icon;
        @SerializedName("name")
        String name;
        @SerializedName("place_id")
        String placeId;
        @SerializedName("rating")
        double rating;
        @SerializedName("vicinity")
        String address;

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


    public class Geometry implements Serializable {
        @SerializedName("location")
        RestaurantLocation location;

        public RestaurantLocation getLocation() {
            return location;
        }

        public void setLocation(RestaurantLocation location) {
            this.location = location;
        }

    }

    public class RestaurantLocation implements Serializable {
        @SerializedName("lat")
        double latitude;
        @SerializedName("lng")
        double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
