package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantrent.ActConst;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Restaurant;

import com.example.restaurantrent.services.HttpService;

import java.util.ArrayList;

public class RegisterRestaurantActivity extends AppCompatActivity {
    public static long idRestaurant;
    private EditText restaurantNameEditText;
    private EditText restaurantAddressEditText;
    public static ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    public static Activity registerRestaurantActivityThis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerRestaurantActivityThis = this;
        restaurantAddressEditText = findViewById(R.id.restaurantAdressEditText);
        restaurantNameEditText = findViewById(R.id.restaurantNameEditText);
        Button toAddRestaurantActivityButton = findViewById(R.id.toAddRestaurantButton);
        toAddRestaurantActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameRestaurant = restaurantNameEditText.getText().toString();
                String addressRestaurant = restaurantAddressEditText.getText().toString();
                if(nameRestaurant.isEmpty()&&addressRestaurant.isEmpty()){
                    Toast.makeText(RegisterRestaurantActivity.this,"Поля не должны быть пустыми",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent i = new Intent(RegisterRestaurantActivity.this, HttpService.class);
                    i.putExtra("act", ActConst.RESTAURANT_REGISTER_ACT);
                    i.putExtra("name",nameRestaurant);
                    i.putExtra("address",addressRestaurant);
                    i.putExtra("idOwner",MainActivity.idOwner+"");
                    startService(i);
                }
            }
        });
    }

}
