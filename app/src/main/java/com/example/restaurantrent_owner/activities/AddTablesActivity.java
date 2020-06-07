package com.example.restaurantrent_owner.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent_owner.Helper;
import com.example.restaurantrent_owner.R;
import com.example.restaurantrent_owner.Server;

import java.util.ArrayList;

// activity для добавления столов в кнокретный ресторан
public class AddTablesActivity extends AppCompatActivity {

    // массив созданных столов
    public static ArrayList<Button> buttons = new ArrayList<Button>();
    // фон для стола
    private Drawable tableDrawable;
    private TextView dropTextView;
    // поле для закрытия этого activity из другого класса
    public static Activity addTablesActivityThis;

    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tables);
        addTablesActivityThis = this;

        dropTextView = findViewById(R.id.dropTextView);

        tableDrawable = getDrawable(R.drawable.table);

        Button button = new Button(AddTablesActivity.this);
        // добавляем кнопку в окно
        addContentView(button, new ViewGroup.LayoutParams(250, 250));
        // делаем кнопку ненажимаемой
        button.setClickable(false);
        // ставим фон кнопки
        button.setBackground(tableDrawable);
        // вызываем метод добавления новой кнопки
        addButton(button);
    }

    public void saveButtons(View view) {

        // пытаемся закрыть RegisterRestaurantActivity
        try {

            RegisterRestaurantActivity.registerRestaurantActivityThis.finish();
            // обрабатываем ошибку
        } catch (Exception e) {
            // закрываем ViewTablesActivity

            ViewTablesActivity.viewTablesActivityThis.finish();
        }
        for (int index = 0; i < buttons.size(); i++) {
            // проверяем не находится ли кнопка в области удаления кнопок
            if (buttons.get(index).getX() >= dropTextView.getX() - 40 && buttons.get(index).getX() <= dropTextView.getX() + 40 && buttons.get(index).getY() >= dropTextView.getY() - 40 && buttons.get(index).getY() <= dropTextView.getX() + 40) {
                // удаляем кнопку
                buttons.remove(buttons.get(index));
            }
        }
        // отправляем запрос добавления столов на сервер
        Server.tableAdd(Helper.convertButtonsFromTables(buttons, getIntent().getLongExtra("idRestaurant", -1)), AddTablesActivity.this);
        buttons.clear();


    }

    @SuppressLint("ClickableViewAccessibility")
    // метод для добавления и перетаскивания кнопок по экрану
    public void addButton(Button button) {
        // отслеживаем нажатие на кнопку
        button.setOnTouchListener(new View.OnTouchListener() {
            float dX;
            float dY;

            @SuppressLint("ResourceType")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // отслеживаем действия с кнопкой
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        // устанавливаем координаты кнопки
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        // перемещаем кнопку за пальцем
                        view.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        Button button = new Button(AddTablesActivity.this);
                        button.setClickable(false);
                        Button b = (Button) view;

                        // проверяем не находится ли кнопка в области удаления кнопок
                        if (view.getX() >= dropTextView.getX() - 40 && view.getX() <= dropTextView.getX() + 40 && view.getY() >= dropTextView.getY() - 40 && view.getY() <= dropTextView.getX() + 40) {
                            // делаем кнопку невидимой
                            view.setVisibility(Button.INVISIBLE);
                            // проверяем новая ли кнопка
                        } else if (b.getText().toString().isEmpty()) {
                            b.setText("" + i++);
                            // добавляем новую кнопку
                            buttons.add(b);
                            // если кнопка уже была добавлена и её перетаскивают в другое место
                        } else if (buttons.size() > Integer.parseInt(b.getText().toString()) - 1) {
                            // меняем координаты уже существующей кнопки в массиве
                            buttons.set(Integer.parseInt(b.getText().toString()) - 1, b);
                        }

                        // ставим фон кнопки
                        button.setBackground(tableDrawable);
                        // добавляем кнопку в окно
                        addContentView(button, new ViewGroup.LayoutParams(250, 250));
                        // вызываем метод добавления новой кнопки
                        addButton(button);

                        break;
                    default:
                        return true;
                }

                return true;
            }
        });
    }

}
