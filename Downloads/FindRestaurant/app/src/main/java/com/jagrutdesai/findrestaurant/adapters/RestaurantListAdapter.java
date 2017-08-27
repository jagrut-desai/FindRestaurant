package com.jagrutdesai.findrestaurant.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jagrutdesai.findrestaurant.R;
import com.jagrutdesai.findrestaurant.activities.RestaurantListActivity;
import com.jagrutdesai.findrestaurant.data.RestaurantInfoDetail;
import com.jagrutdesai.findrestaurant.data.Restaurants;
import com.jagrutdesai.findrestaurant.network.RetrofitClient;
import com.jagrutdesai.findrestaurant.network.interfaces.RestaurantApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by jagrut.desai on 8/26/17.
 */

/**
 * Adapter for showing list data. Using ViewHolder for smooth scrolling and recycle of the views.
 */
public class RestaurantListAdapter extends ArrayAdapter<Restaurants.Restaurant> {

    private List<Restaurants.Restaurant> restaurantList;
    private Context mContext;
    private Retrofit retrofitClient;


    public RestaurantListAdapter(@NonNull Context context, @LayoutRes int resource, List<Restaurants.Restaurant> restaurantList) {
        super(context, resource);
        this.restaurantList = restaurantList;
        this.mContext = context;
        retrofitClient = RetrofitClient.getRetrofit();
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    /**
     * Return the view made with viewhodler and load temp image.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.restaurant_list_item, null);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.name = convertView.findViewById(R.id.name);
            holder.rating = convertView.findViewById(R.id.rating);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurants.Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.rating.setText(restaurant.getRating() + " Stars");
        getRestaurntDetail(restaurant.getPlaceId(), holder.image);
        return convertView;
    }

    /**
     * View Holder for the List.
     */
    static class ViewHolder {
        private TextView name;
        private TextView rating;
        private ImageView image;
    }

    /**
     * Get Restaurant detail from place Id which was passed from previous activity,
     */
    public void getRestaurntDetail(String placeId, final ImageView image){

        RestaurantApiInterface restaurantApiInterface = retrofitClient.create(RestaurantApiInterface.class);
        final Call<RestaurantInfoDetail> restaurantDetailCall =
                restaurantApiInterface.getRestaurantDetail(placeId, RetrofitClient.API_KEY);
        restaurantDetailCall.enqueue(new Callback<RestaurantInfoDetail>() {
            @Override
            public void onResponse(Call<RestaurantInfoDetail> call, Response<RestaurantInfoDetail> response) {
                RestaurantInfoDetail.RestaurantDetail restaurantDetail = response.body().getRestaurantDetail();
                getRestaurantPhoto(restaurantDetail.getPhotos().get(0).getPhotoReference(), image);
            }

            @Override
            public void onFailure(Call<RestaurantInfoDetail> call, Throwable t) {

            }
        });
    }

    /**
     * Get photo for the restaurant.
     */
    public void getRestaurantPhoto(String photoreference, final ImageView image){
        RestaurantApiInterface restaurantApiInterface = retrofitClient.create(RestaurantApiInterface.class);
        String photoReference = photoreference;
        final Call<ResponseBody> restaurantDetailCall = restaurantApiInterface.getRestaurantPhoto(photoReference,RetrofitClient.API_KEY, "700");
        restaurantDetailCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Picasso.with(mContext).load(response.raw().request().url().toString()).into(image);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
