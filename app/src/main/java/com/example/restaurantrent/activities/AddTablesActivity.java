package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.restaurantrent.ActConst;
import com.example.restaurantrent.R;
import com.example.restaurantrent.Table;

import com.example.restaurantrent.services.HttpService;

import java.util.ArrayList;

public class AddTablesActivity extends AppCompatActivity {
    private Button b;
    private int i = 1;
    public static ArrayList<Table> tables = new ArrayList<>();
    private Drawable tableDrawble;

    public static ArrayList<Button> buttons = new ArrayList<Button>();

    public static Activity addTablesActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tables);

        addTablesActivityThis = this;

        tableDrawble = getDrawable(R.drawable.table);
        System.out.println(RegisterRestaurantActivity.idRestaurant);
        Button button = new Button(AddTablesActivity.this);
        addContentView(button,new ViewGroup.LayoutParams(250,250));
        button.setClickable(false);
        button.setBackground(tableDrawble);
        addButton(button);
    }
    public void saveButtons(View view){

        tables.clear();
      for(Button temp: buttons){
          Intent i = new Intent(AddTablesActivity.this, HttpService.class);
          i.putExtra("act", ActConst.SET_TABLES_ACT);
          i.putExtra("idOwner",RegisterRestaurantActivity.idRestaurant);
          i.putExtra("x",temp.getX());
          i.putExtra("y",temp.getY());
          startService(i);
      }
      buttons.clear();
        Intent intent = new Intent(AddTablesActivity.this, HttpService.class);
        intent.putExtra("act",ActConst.GET_TABLES_ACT);
        intent.putExtra("idRestaurant",RegisterRestaurantActivity.idRestaurant);
        startService(intent);



    }

    @SuppressLint("ClickableViewAccessibility")
    public void addButton(Button button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            float dY;

            @SuppressLint("ResourceType")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        Button button = new Button(AddTablesActivity.this);
                        button.setClickable(false);
                        b = (Button) view;
                        if (b.getX() > 50 || b.getY() > 50) {
                            if (b.getText().toString().isEmpty()) {
                                b.setText(""+i++);
                                buttons.add(b);
                            } else {
                                buttons.set(Integer.parseInt(b.getText().toString()) - 1, b);
                            }
                            button.setBackground(tableDrawble);
                            addContentView(button, new ViewGroup.LayoutParams(250, 250));
                            addButton(button);
                        }

                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
    }

}
