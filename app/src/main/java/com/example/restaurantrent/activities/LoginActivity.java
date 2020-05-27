package com.example.restaurantrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantrent.ActConst;
import com.example.restaurantrent.R;

import com.example.restaurantrent.services.HttpService;

public class LoginActivity extends AppCompatActivity {
    private TextView registrationTextView;
    private EditText emailEditText;
    private EditText passwordEditText;

    public static Activity loginActivityThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginActivityThis = this;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registrationTextView = findViewById(R.id.registrationTextView);
        registrationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    public void toMain(View view){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(email.isEmpty()&&password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Поля не должны быть пустыми", Toast.LENGTH_LONG).show();
        }
        else {
                if(isEmailCorrect(email)){
                    if(password.length() >= 8){
                        Intent i = new Intent(LoginActivity.this, HttpService.class);
                        i.putExtra("act", ActConst.LOGIN_ACT);
                        i.putExtra("email",email);
                        i.putExtra("password",password);

                        registrationTextView.setVisibility(TextView.INVISIBLE);
                        emailEditText.setVisibility(EditText.INVISIBLE);
                        passwordEditText.setVisibility(EditText.INVISIBLE);
                        ((Button)findViewById(R.id.button)).setVisibility(Button.INVISIBLE);
                        ((TextView)findViewById(R.id.textView)).setVisibility(Button.INVISIBLE);
                        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
                        progressBar.setVisibility(ProgressBar.VISIBLE);

                        startService(i);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Пароль должен содержать 8 или более символов",Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this,"Формат электронной почты не корректен",Toast.LENGTH_SHORT).show();
                }

            }
        }
    public static boolean isEmailCorrect(String email){
        if(email.contains("@")){
            email = email.split("@")[1];
            if(email.contains(".")){
                return true;
            }
        }
        return false;
    }
}
