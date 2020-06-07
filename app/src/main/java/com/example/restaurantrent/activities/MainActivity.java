package com.example.restaurantrent.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurantrent.Owner;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Rent;
import com.example.restaurantrent.Restaurant;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

// activity для отображения фрагментов
public class MainActivity extends AppCompatActivity {

    // поле владельца
    public static Owner owner = new Owner();
    // массив ресторанов владельца
    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    // массив заказов, отправленных владельцу
    public static ArrayList<Rent> rents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // берём владельца из класса Server
        owner = (Owner) getIntent().getSerializableExtra("owner");

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
