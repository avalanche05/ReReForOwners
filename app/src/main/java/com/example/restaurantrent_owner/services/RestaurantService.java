package com.example.restaurantrent_owner.services;

import com.example.restaurantrent_owner.Restaurant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RestaurantService {
    @POST("restaurant/delete")
    Call<String> restaurantDelete(@Query("id") Long id);

    @POST("restaurant/add")
    Call<Restaurant> restaurantAdd(@Body Restaurant restaurant);

    @POST("restaurant/get")
    Call<ArrayList<Restaurant>> getRestaurants(@Query("idOwner") Long idOwner);
}
