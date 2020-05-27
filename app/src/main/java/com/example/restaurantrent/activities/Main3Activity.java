package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.restaurantrent.ActConst;
import com.example.restaurantrent.JsonParser;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Table;

import com.example.restaurantrent.services.HttpService;

import org.json.JSONException;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private Button deleteButton;

    public static int index = 0;

    public static ArrayList<Table> tables = new ArrayList<>();

    public static Activity main3ActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        main3ActivityThis = this;

        deleteButton = new Button(this);
        deleteButton.setText(" Удалить бронь ");
        deleteButton.setTextColor(Color.WHITE);
        deleteButton.setBackground(getDrawable(R.drawable.button));
        deleteButton.setX(2);
        deleteButton.setY(60);
        deleteButton.setVisibility(Button.INVISIBLE);
        deleteButton.setOnClickListener(this);
        addContentView(deleteButton,new ViewGroup.LayoutParams(300,140));
        for(int i = 0; i < tables.size(); i++){
            float x = tables.get(i).getX();
            float y = tables.get(i).getY();
            int width = 250;
            int height = 250;
            Button b = new Button(this);
            b.setY(y);
            b.setX(x);
            b.setId((int)tables.get(i).getId());
            System.out.println(tables.get(i).getId());


            try {
                if(Boolean.parseBoolean(getIntent().getStringExtra("isRent"))&& isIdTablesContains(JsonParser.getIdTables(MainActivity.rents.get(index).getIdTables()),b.getId())){
                    b.setBackground(getDrawable(R.drawable.table_pressed));
                    deleteButton.setVisibility(Button.VISIBLE);
                }
                else {
                    b.setBackground(getDrawable(R.drawable.table));
                }
            } catch (JSONException e) {
                b.setBackground(getDrawable(R.drawable.table));
                e.printStackTrace();
            }

            addContentView(b,new ViewGroup.LayoutParams(250,250));
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Кнопка удалить бронь нажата");
                Intent i = new Intent(Main3Activity.this,HttpService.class);
                i.putExtra("act",ActConst.DELETE_RENT_ACT);
                i.putExtra("id",MainActivity.rents.get(index).getId());
                startService(i);
            }
        });


    }
    public void finishAddTable(View view){
        if(Boolean.parseBoolean(getIntent().getStringExtra("isRent"))){
            finish();
        }
        else {
            RegisterRestaurantActivity.registerRestaurantActivityThis.finish();
            AddTablesActivity.addTablesActivityThis.finish();
            finish();
        }
    }

    public boolean isIdTablesContains(ArrayList<Integer> idTables,int idTable){
        for (int temp : idTables) {
            if (temp == idTable) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(Main3Activity.this,HttpService.class);
        i.putExtra("act",ActConst.DELETE_RENT_ACT);
        i.putExtra("id",MainActivity.rents.get(Main3Activity.index).getId());
        startService(i);
    }
}
