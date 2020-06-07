package com.example.restaurantrent_owner.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantrent_owner.Helper;
import com.example.restaurantrent_owner.R;
import com.example.restaurantrent_owner.Server;

// activity для входа в приложение
public class LoginActivity extends AppCompatActivity {

    private TextView registrationTextView;
    private EditText emailEditText;
    private EditText passwordEditText;

    // поле для закрытия этого activity из другого класса
    public static Activity loginActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginActivityThis = this;

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registrationTextView = findViewById(R.id.registrationTextView);

        // проверяем, не входил ли пользователь ранее
        SharedPreferences preferences = getSharedPreferences(Helper.APP_PREFERENCES, Context.MODE_PRIVATE);
        if (preferences.contains(Helper.APP_PREFERENCES_EMAIL) && preferences.contains(Helper.APP_PREFERENCES_PASSWORD)) {
            String email = preferences.getString(Helper.APP_PREFERENCES_EMAIL, "");
            String password = preferences.getString(Helper.APP_PREFERENCES_PASSWORD, "");
            // отправляем запрос входа на сервер
            // loginOwner принимает контекст, для отправки Toast и создания Intent, ProgressBar, который нужно показывать при загрузке и окна, которые нужно сделать невидимыми во время обращения к серверу
            Server.loginOwner(LoginActivity.this, email, password, (ProgressBar) findViewById(R.id.progressBar), emailEditText, passwordEditText, registrationTextView, findViewById(R.id.textView), findViewById(R.id.loginButton));
        }

        // отслеживаем нажатие на TextView для перехода к регистрации
        registrationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // переходим к activity регистрации
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    // метод отслеживающий нажатие кнопки входа
    public void toMain(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // проверяем корректность электронной почты и пароля
        if (Helper.isLoginDataCorrect(email, password, LoginActivity.this)) {
            // отправляем запрос входа на сервер
            // loginUser принимает контекст, для отправки Toast и создания Intent, ProgressBar, который нужно показывать при загрузке и окна, которые нужно сделать невидимыми во время загрузки
            Server.loginOwner(LoginActivity.this, email, password, (ProgressBar) findViewById(R.id.progressBar), emailEditText, passwordEditText, registrationTextView, findViewById(R.id.textView), findViewById(R.id.loginButton));
        }
    }



}
