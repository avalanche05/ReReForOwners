package com.example.restaurantrent_owner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent_owner.Board;
import com.example.restaurantrent_owner.Helper;
import com.example.restaurantrent_owner.R;
import com.example.restaurantrent_owner.Server;

import java.util.ArrayList;

// activity для просмотра столов конкретного ресторана
public class ViewTablesActivity extends AppCompatActivity {

    // поле для закрытия этого activity из другого класса
    public static Activity viewTablesActivityThis;

    // поле текущего индекса заказа(присваевается в DashboardFragment)
    public static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tables);

        viewTablesActivityThis = this;
        // берём массив столов конкретного ресторана из класса Server
        ArrayList<Board> tables = (ArrayList<Board>) getIntent().getSerializableExtra("tables");
        for (Board table : tables) {
            // переводим класс Board в класс Button
            Button button = Helper.convertTableFromButton(table, this);
            // добавляем кнопку в окно
            addContentView(button, new ViewGroup.LayoutParams(250, 250));
        }
    }

    // метод отслеживающий нажатие кнопки редактирования расстановки столов
    public void editButton(View view) {
        // переходим в AddTablesActivity
        Intent i = new Intent(ViewTablesActivity.this, AddTablesActivity.class);
        // кладём в Intent id ресторана
        i.putExtra("idRestaurant", getIntent().getLongExtra("idRestaurant", -1));
        startActivity(i);
    }

    // метод, отслеживающий кнопку кдаления ресторана
    public void deleteRestaurantButton(View view) {
        // отправляем запрос удаления ресторана на сервер
        Server.restaurantDelete(MainActivity.restaurants.get(index).getId(), ViewTablesActivity.this);
        // отправляем запрос списка всех ресторанов конкретного владельца
        Server.getRestaurants(MainActivity.owner.getId());
        // закрываем текущее activity
        finish();
    }

    // метод, отслеживающий кнопку отмены создания заказа
    public void cancelViewTables(View view) {
        // закрываем текущее activity
        finish();
    }
}
