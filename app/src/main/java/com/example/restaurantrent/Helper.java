package com.example.restaurantrent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

// класс помощник
public class Helper {

    // поля для SharedPreferences
    public static final String APP_PREFERENCES = "owner";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_PASSWORD = "password";

    // метод, который проверяет корректность данных для входа
    public static boolean isLoginDataCorrect(String email, String password, Context context) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Ни одно поле не должно быть пустым", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isEmailCorrect(email)) {
            Toast.makeText(context, "Формат электронной почты не корректен", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            Toast.makeText(context, "Пароль должен содержать " + MINIMUM_PASSWORD_LENGTH + " или более символов", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // метод, который проверяет корректность данных для регистрации
    public static boolean isSignUpDataCorrect(String email, String password, Context context) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Ни одно поле не должно быть пустым", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isEmailCorrect(email)) {
            Toast.makeText(context, "Формат электронной почты не корректен", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            Toast.makeText(context, "Пароль должен содержать " + MINIMUM_PASSWORD_LENGTH + " или более символов", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // метод переводит класс Board в класс Button
    public static Button convertTableFromButton(Board table, Activity activity) {
        Button button = new Button(activity);
        button.setId((int) table.getId());
        button.setX(table.getX());
        button.setY(table.getY());
        button.setBackground(activity.getDrawable(R.drawable.table));
        button.setTextColor(Color.WHITE);
        return button;
    }

    // метод переводит массив Button в массив Board
    public static ArrayList<Board> convertButtonsFromTables(ArrayList<Button> buttons, Long idRestaurant) {
        ArrayList<Board> tables = new ArrayList<>();
        for (Button temp : buttons) {
            if (temp.getX() >= 338 && temp.getX() <= 408 && temp.getY() >= -30 && temp.getY() <= 45) {

            } else {
                Board table = new Board(idRestaurant, temp.getX(), temp.getY());
                tables.add(table);
            }

        }
        return tables;
    }

    // метод находит название месяца по его цифре
    public static String convertDateToString(String date) {
        String[] numberDate = date.split("\\.");
        String[] month = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Ноября", "Октября", "Декабря"};
        return numberDate[0] + " " + month[Integer.parseInt(numberDate[1]) - 1];
    }

    // метод проверяет на пустоту поля
    public static boolean isAllFieldsNotEmpty(String... fields) {
        for (String temp : fields) {
            if (temp.isEmpty())
                return false;
        }
        return true;
    }

    final private static int MINIMUM_PASSWORD_LENGTH = 8;

    // метод проверят корректность электронной почты
    private static boolean isEmailCorrect(String email) {
        if (email.contains("@")) {
            email = email.split("@")[1];
            if (email.contains(".")) {
                return true;
            }
        }
        return false;
    }

}
