package com.example.restaurantrent.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent.Helper;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Restaurant;
import com.example.restaurantrent.Server;

// activity для регистрации нового ресторана
public class RegisterRestaurantActivity extends AppCompatActivity {

    private EditText restaurantNameEditText;
    private EditText restaurantAddressEditText;

    // поля для закрытия этого activity из другого класса
    public static Activity registerRestaurantActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerRestaurantActivityThis = this;

        restaurantAddressEditText = findViewById(R.id.restaurantAdressEditText);
        restaurantNameEditText = findViewById(R.id.restaurantNameEditText);

    }

    public void registerRestaurantButton(View view) {

        String name = restaurantNameEditText.getText().toString();
        String address = restaurantAddressEditText.getText().toString();

        // проверяем не вводит для владелец пустые значения
        if (Helper.isAllFieldsNotEmpty(name, address)) {
            // отправляем запрос регистрации нового ресторана на сервер
            Server.restaurantAdd(new Restaurant(MainActivity.owner.getId(), name, address), RegisterRestaurantActivity.this, new Intent(RegisterRestaurantActivity.this, AddTablesActivity.class));
        }
    }

}
