package com.example.restaurantrent.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantrent.ActConst;
import com.example.restaurantrent.JsonParser;
import com.example.restaurantrent.R;
import com.example.restaurantrent.activities.AddTablesActivity;
import com.example.restaurantrent.activities.LoginActivity;
import com.example.restaurantrent.activities.Main3Activity;
import com.example.restaurantrent.activities.MainActivity;
import com.example.restaurantrent.activities.RegisterRestaurantActivity;
import com.example.restaurantrent.activities.SignUpActivity;
import com.example.restaurantrent.builders.ParameterStringBuilder;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HttpService extends Service {

    private static final String DOMAIN = "https://restaurant-rent-server.herokuapp.com";

    public static final String CHANNEL = "GIS_SERVICE";
    public static final String INFO = "INFO";

    @Override
    public void onCreate() {
        // сообщение о создании службы
        System.out.println("СЕРВИС СОЗДАН");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // сообщение о запуске службы
        System.out.println("СЕРВИС ЗАПУЩЕН");
        switch (intent.getIntExtra("act",-1)){
            case ActConst.SIGNUP_ACT:
                SignUpTask signUpTask = new SignUpTask();
                signUpTask.execute(intent.getStringExtra("email"),intent.getStringExtra("password"));
                break;
            case ActConst.LOGIN_ACT:
                LoginTask loginTask = new LoginTask();
                loginTask.execute(intent.getStringExtra("email"),intent.getStringExtra("password"));
                break;
            case ActConst.GET_OWNER_ACT:
                GetTask getTask = new GetTask();
                getTask.execute(intent.getLongExtra("idOwner",-1)+"");
                break;
            case ActConst.RESTAURANT_REGISTER_ACT:
                RestaurantRegisterTask restaurantRegisterTask = new RestaurantRegisterTask();
                restaurantRegisterTask.execute(intent.getStringExtra("name"),intent.getStringExtra("address"),intent.getStringExtra("idOwner"));
                break;
            case ActConst.SET_TABLES_ACT:
                SetTablesTask setTablesTask = new SetTablesTask();
                setTablesTask.execute(intent.getLongExtra("idOwner",-1)+"",intent.getFloatExtra("x",-1)+"",intent.getFloatExtra("y",-1)+"");
                break;
            case ActConst.GET_TABLES_ACT:
                GetTablesTask getTablesTask = new GetTablesTask();
                getTablesTask.execute(intent.getLongExtra("idRestaurant",-1)+"",intent.getBooleanExtra("isRent",false)+"");
                break;
            case ActConst.GET_RESTAURANTS_ACT:
                GetRestaurantsTask getRestaurantsTask = new GetRestaurantsTask();
                getRestaurantsTask.execute(intent.getLongExtra("idOwner",-1)+"");
                break;
            case ActConst.GET_RENTS_ACT:
                GetRentsTask getRentsTask = new GetRentsTask();
                getRentsTask.execute(intent.getLongExtra("idOwner",-1)+"");
                break;
            case ActConst.DELETE_RENT_ACT:
                DeleteRentTask deleteRentTask = new DeleteRentTask();
                deleteRentTask.execute(intent.getLongExtra("id",-1)+"");
                break;
            default:
                break;
        }


        // создаем объект нашего AsyncTask (необходимо для работы с сетью)
         // запускаем AsyncTask

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //сообщение об остановке службы
        Toast.makeText(this, "Service stoped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //поток работы с сетью
    private class SignUpTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN+"/owner/add");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("email", args[0]);
                parameters.put("password", args[1]);


                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " +status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);
                if(result.contains("Пользователь уже зарегистрирован")){
                    SignUpActivity.signUpActivityThis.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SignUpActivity.signUpActivityThis, "Пользователь с такой электронной почтой зарегистрирован", Toast.LENGTH_LONG).show();
                        }
                    });
                    SignUpActivity.signUpActivityThis.finish();
                    startActivity(new Intent(HttpService.this,SignUpActivity.class));
                }
                else {
                    SignUpActivity.signUpActivityThis.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(SignUpActivity.signUpActivityThis, "Пользователь успешно зарегистрирован!", Toast.LENGTH_LONG).show();
                        }
                    });
                    SignUpActivity.signUpActivityThis.finish();
                }


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/owner/login");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("email", args[0]);
                parameters.put("password", args[1]);


                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                Intent i = new Intent(HttpService.this,HttpService.class);

                try {
                    i.putExtra("act",ActConst.GET_OWNER_ACT);
                    i.putExtra("idOwner",Long.parseLong(result));
                    MainActivity.idOwner = Long.parseLong(result);
                    startService(i);
                } catch (Exception e) {
                    LoginActivity.loginActivityThis.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.loginActivityThis,"Не верный логин или пароль",Toast.LENGTH_SHORT).show();
                        }
                    });

                    LoginActivity.loginActivityThis.finish();
                    startActivity(new Intent(HttpService.this,LoginActivity.class));

                }


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }

        private class GetTask extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String aVoid) {
                Intent i = new Intent(CHANNEL); // интент для отправки ответа
                i.putExtra(INFO, aVoid); // добавляем в интент данные
                sendBroadcast(i); // рассылаем
            }

            @Override
            protected String doInBackground(String... args) {
                String result = "";
                try {
                    //загружаем данные
                    URL url = new URL(DOMAIN+"/owner/get");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    Map<String, String> parameters = new HashMap<>();

                    parameters.put("id", args[0]);


                    con.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(con.getOutputStream());
                    out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                    out.flush();
                    out.close();

                    int status = con.getResponseCode();
                    System.out.println("СТАТУС: " +status);

                    Scanner in = new Scanner((InputStream) con.getInputStream());
                    result = in.nextLine();

                    System.out.println("РЕЗУЛЬТАТЫ: " + result);
                    Intent i = new Intent(HttpService.this,MainActivity.class);
                    i.putExtra("info",result);
                    startActivity(i);
                    LoginActivity.loginActivityThis.finish();

                } catch (Exception e) {
                    result = e.toString();
                    System.out.println("ОШИБКА: " + result);
                }
                return result;

            }
    }
    private class RestaurantRegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN+"/restaurant/add");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("name", args[0]);
                parameters.put("address", args[1]);
                parameters.put("idOwner", args[2]);


                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " +status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);
                Intent i = new Intent(HttpService.this, AddTablesActivity.class);
                RegisterRestaurantActivity.idRestaurant = Integer.parseInt(result);
                startActivity(i);

            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class SetTablesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/table/add");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();


                parameters.put("idRestaurant", args[0]);
                parameters.put("x", args[1]);
                parameters.put("y", args[2]);



                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);




            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class GetTablesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/table/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("idRestaurant", args[0]);



                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                Main3Activity.tables = JsonParser.getTables(result);

                Intent i = new Intent(HttpService.this,Main3Activity.class);
                i.putExtra("isRent",args[1]);
                startActivity(i);


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class GetRestaurantsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN + "/restaurant/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("idOwner", args[0]);



                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " + status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                MainActivity.restaurants = JsonParser.getRestaurants(result);


            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    //поток работы с сетью
    private class GetRentsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN+"/owner/rent/get");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("idOwner", args[0]);


                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " +status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                MainActivity.rents = JsonParser.getRents(result);

            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }
    private class DeleteRentTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String aVoid) {
            Intent i = new Intent(CHANNEL); // интент для отправки ответа
            i.putExtra(INFO, aVoid); // добавляем в интент данные
            sendBroadcast(i); // рассылаем
        }

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            try {
                //загружаем данные
                URL url = new URL(DOMAIN+"/rent/delete");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                Map<String, String> parameters = new HashMap<>();

                parameters.put("id", args[0]);


                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
                out.flush();
                out.close();

                int status = con.getResponseCode();
                System.out.println("СТАТУС: " +status);

                Scanner in = new Scanner((InputStream) con.getInputStream());
                result = in.nextLine();

                System.out.println("РЕЗУЛЬТАТЫ: " + result);

                Main3Activity.main3ActivityThis.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main3Activity.main3ActivityThis,"Бронь удалена",Toast.LENGTH_SHORT).show();
                    }
                });
                Main3Activity.main3ActivityThis.finish();

            } catch (Exception e) {
                result = e.toString();
                System.out.println("ОШИБКА: " + result);
            }
            return result;

        }
    }

}

