package com.jagrutdesai.findrestaurant.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jagrut.desai on 8/26/17.
 */

/**
 * More detail info about the restaurant. Used to show data on detail activity.
 */
public class RestaurantInfoDetail implements Serializable {
    @SerializedName("result")
    RestaurantDetail restaurantDetail;

    public class RestaurantDetail implements Serializable {
        @SerializedName("formatted_address")
        String address;
        @SerializedName("formatted_phone_number")
        String phoneNumber;
        @SerializedName("name")
        String name;
        @SerializedName("rating")
        double rating;
        @SerializedName("photos")
        List<Photo> photos;
        @SerializedName("reviews")
        List<Review> reviews;


        public class Photo implements Serializable {
            @SerializedName("photo_reference")
            String photoReference;

            public String getPhotoReference() {
                return photoReference;
            }

            public void setPhotoReference(String photoReference) {
                this.photoReference = photoReference;
            }
        }

        public class Review implements Serializable {
            @SerializedName("author_name")
            String authorName;
            @SerializedName("author_url")
            String authorUrl;
            @SerializedName("language")
            String language;
            @SerializedName("profile_photo_url")
            String authorImageUrl;
            @SerializedName("rating")
            double rating;
            @SerializedName("relative_time_description")
            String timeDescription;
            @SerializedName("text")
            String reviewText;
            @SerializedName("time")
            Long time;

            public String getAuthorName() {
                return authorName;
            }

            public void setAuthorName(String authorName) {
                this.authorName = authorName;
            }

            public String getAuthorUrl() {
                return authorUrl;
            }

            public void setAuthorUrl(String authorUrl) {
                this.authorUrl = authorUrl;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getAuthorImageUrl() {
                return authorImageUrl;
            }

            public void setAuthorImageUrl(String authorImageUrl) {
                this.authorImageUrl = authorImageUrl;
            }

            public double getRating() {
                return rating;
            }

            public void setRating(double rating) {
                this.rating = rating;
            }

            public String getTimeDescription() {
                return timeDescription;
            }

            public void setTimeDescription(String timeDescription) {
                this.timeDescription = timeDescription;
            }

            public String getReviewText() {
                return reviewText;
            }

            public void setReviewText(String reviewText) {
                this.reviewText = reviewText;
            }

            public Long getTime() {
                return time;
            }

            public void setTime(Long time) {
                this.time = time;
            }
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public void setReviews(List<Review> reviews) {
            this.reviews = reviews;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public RestaurantDetail getRestaurantDetail() {
        return restaurantDetail;
    }

    public void setRestaurantDetail(RestaurantDetail restaurantDetail) {
        this.restaurantDetail = restaurantDetail;
    }
}
