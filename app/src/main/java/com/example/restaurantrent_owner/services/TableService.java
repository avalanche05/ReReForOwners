package com.example.restaurantrent_owner.services;

import com.example.restaurantrent_owner.Board;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TableService {
    @POST("table/add")
    Call<String> tableAdd(@Body ArrayList<Board> tables);

    @POST("/table/get")
    Call<ArrayList<Board>> tableGet(@Query("idRestaurant") Long idRestaurant);
}
