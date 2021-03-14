package com.example.restaurantrent_owner.services;

import com.example.restaurantrent_owner.Rent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RentService {
    @POST("rent/delete")
    Call<String> rentDelete(@Query("id") Long id);

    @POST("rent/owner/get")
    Call<ArrayList<Rent>> getOwnerRent(@Query("idOwner") Long idOwner);
}
