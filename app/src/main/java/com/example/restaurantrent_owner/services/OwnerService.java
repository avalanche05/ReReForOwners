package com.example.restaurantrent_owner.services;

import com.example.restaurantrent_owner.Owner;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OwnerService {

    @POST("owner/confirm")
    Call<Boolean> isConfirm(@Query("id") Long id);

    @POST("owner/signup")
    Call<Owner> signUpOwner(@Query("email") String email, @Query("password") String password);

    @POST("owner/login")
    Call<Owner> loginOwner(@Query("email") String email, @Query("password") String password);

}
