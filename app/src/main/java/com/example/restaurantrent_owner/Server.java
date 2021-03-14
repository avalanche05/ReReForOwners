package com.example.restaurantrent_owner;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.restaurantrent_owner.activities.ChatActivity;
import com.example.restaurantrent_owner.activities.MainActivity;
import com.example.restaurantrent_owner.adapters.MessageAdapter;
import com.example.restaurantrent_owner.services.MessageService;
import com.example.restaurantrent_owner.services.RentService;
import com.example.restaurantrent_owner.services.RestaurantService;
import com.example.restaurantrent_owner.services.TableService;
import com.example.restaurantrent_owner.services.OwnerService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Server {


    public static void loginOwner(final Activity activity, final String email, final String password, final ProgressBar progressBar, final View... views) {
        showProgressBar(progressBar, views);
        Call<Owner> callUser = Server.ownerService.loginOwner(email, password);
        callUser.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                Owner owner = response.body();
                if (owner.getEmail() == null) {
                    Toast.makeText(activity, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                    hideProgressBar(progressBar, views);
                } else {
                    putOwnerInSharedPreferences(owner, activity);
                    getOwnerRent(owner.getId());
                    getRestaurants(owner.getId());
                    Intent i = new Intent(activity, MainActivity.class);
                    i.putExtra("owner", owner);
                    activity.startActivity(i);
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Toast.makeText(activity, "Повторное подключение к серверу", Toast.LENGTH_SHORT).show();
                loginOwner(activity, email, password, progressBar, views);
            }
        });
    }

    public static void signUpOwner(final Activity activity, final String email, final String password, final ProgressBar progressBar, final View... views) {
        showProgressBar(progressBar, views);
        Call<Owner> callUser = Server.ownerService.signUpOwner(email, password);
        callUser.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                Owner owner = response.body();
                if (owner.getEmail() == null) {
                    Toast.makeText(activity, "Пользователь с такой электронной почтой уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    hideProgressBar(progressBar, views);
                } else {
                    putOwnerInSharedPreferences(owner, activity);
                    getRestaurants(owner.getId());
                    getOwnerRent(owner.getId());
                    Intent i = new Intent(activity, MainActivity.class);
                    i.putExtra("owner", owner);
                    activity.startActivity(i);
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Toast.makeText(activity, "Повторное подключение к серверу", Toast.LENGTH_SHORT).show();
                signUpOwner(activity, email, password, progressBar, views);
            }
        });
    }

    public static void getRestaurants(final Long idOwner) {
        Call<ArrayList<Restaurant>> callAllRestaurants = restaurantService.getRestaurants(idOwner);
        callAllRestaurants.enqueue(new Callback<ArrayList<Restaurant>>() {
            @Override
            public void onResponse(Call<ArrayList<Restaurant>> call, Response<ArrayList<Restaurant>> response) {
                MainActivity.restaurants.clear();
                for (Restaurant temp : response.body()) {
                    MainActivity.restaurants.add(temp);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Restaurant>> call, Throwable t) {
                getRestaurants(idOwner);
            }
        });
    }

    public static void restaurantAdd(final Restaurant restaurant, final Activity activity, final Intent intent) {
        Call<Restaurant> callRestaurant = restaurantService.restaurantAdd(restaurant);
        callRestaurant.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                getRestaurants(MainActivity.owner.getId());
                intent.putExtra("idRestaurant", response.body().getId());
                activity.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                restaurantAdd(restaurant, activity, intent);
            }
        });
    }

    public static void restaurantDelete(final Long id, final Activity activity) {
        Call<String> callString = restaurantService.restaurantDelete(id);
        callString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(activity, "Ресторан успешно удалён!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                restaurantDelete(id, activity);
            }
        });
    }

    public static void getOwnerRent(final Long idOwner) {
        Call<ArrayList<Rent>> callUserRent = rentService.getOwnerRent(idOwner);
        callUserRent.enqueue(new Callback<ArrayList<Rent>>() {
            @Override
            public void onResponse(Call<ArrayList<Rent>> call, Response<ArrayList<Rent>> response) {
                MainActivity.rents.clear();
                for (Rent temp : response.body()) {
                    MainActivity.rents.add(temp);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rent>> call, Throwable t) {
                getOwnerRent(idOwner);
            }
        });
    }


    public static void rentDelete(final Long id, final Activity activity) {
        System.out.println(id);
        Call<String> callString = rentService.rentDelete(id);
        callString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(activity, response.body(), Toast.LENGTH_SHORT).show();
                Server.getOwnerRent(MainActivity.owner.getId());
                activity.finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                rentDelete(id, activity);
            }
        });
    }

    public static void tableGet(final Long idRestaurant, final Activity fromActivity, final Intent intent) {
        Call<ArrayList<Board>> callTables = tableService.tableGet(idRestaurant);
        callTables.enqueue(new Callback<ArrayList<Board>>() {
            @Override
            public void onResponse(Call<ArrayList<Board>> call, Response<ArrayList<Board>> response) {
                intent.putExtra("idRestaurant", idRestaurant);
                intent.putExtra("tables", response.body());
                fromActivity.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ArrayList<Board>> call, Throwable t) {
                tableGet(idRestaurant, fromActivity, intent);
            }
        });

    }

    public static void tableAdd(final ArrayList<Board> tables, final Activity activity) {
        Call<String> callString = tableService.tableAdd(tables);
        callString.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(activity, "Ресторан успешно добавлен!", Toast.LENGTH_SHORT).show();
                activity.finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                tableAdd(tables, activity);
            }
        });
    }

    public static void sendMessage(final Message message, final MessageAdapter messageAdapter) {
        Call<Boolean> callBoolean = messageService.messageSend(message);
        callBoolean.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                ChatActivity.messages.add(message);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public static void getMessage(final Long idRent, final Context context, final Intent intent) {
        Call<ArrayList<Message>> callMessage = messageService.messageGet(idRent);
        callMessage.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                ChatActivity.messages = response.body();
                intent.putExtra("idRent", idRent);
                context.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {

            }
        });
    }

    public static void isCondirm(Long id) {
        Call<Boolean> callBoolean = ownerService.isConfirm(id);
        callBoolean.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                MainActivity.owner.setAuth(response.body());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private static void putOwnerInSharedPreferences(Owner owner, Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(Helper.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Helper.APP_PREFERENCES_EMAIL, owner.getEmail());
        editor.putString(Helper.APP_PREFERENCES_PASSWORD, owner.getPassword());
        editor.apply();
    }

    private static void showProgressBar(ProgressBar progressBar, View... views) {
        progressBar.setVisibility(View.VISIBLE);
        for (View temp : views) {
            temp.setVisibility(View.INVISIBLE);
        }
    }

    private static void hideProgressBar(ProgressBar progressBar, View... views) {
        progressBar.setVisibility(View.INVISIBLE);
        for (View temp : views) {
            temp.setVisibility(View.VISIBLE);
        }
    }

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://restaurant-rent-server.herokuapp.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static OwnerService ownerService = retrofit.create(OwnerService.class);
    private static RestaurantService restaurantService = retrofit.create(RestaurantService.class);
    private static RentService rentService = retrofit.create(RentService.class);
    private static TableService tableService = retrofit.create(TableService.class);
    private static MessageService messageService = retrofit.create(MessageService.class);

}
