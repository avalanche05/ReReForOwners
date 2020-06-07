package com.example.restaurantrent_owner.activities.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantrent_owner.Helper;
import com.example.restaurantrent_owner.R;
import com.example.restaurantrent_owner.Server;
import com.example.restaurantrent_owner.activities.LoginActivity;
import com.example.restaurantrent_owner.activities.MainActivity;
import com.example.restaurantrent_owner.activities.RegisterRestaurantActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView emailTextView = root.findViewById(R.id.ownerInformationTextView);
        final Button toRegisterActivityButton = root.findViewById(R.id.toRegisterActivityButton);
        final Button outAccountButton = root.findViewById(R.id.outButton);
        final TextView confirmTextView = root.findViewById(R.id.confirmTextView);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                Server.isCondirm(MainActivity.owner.getId());
                if (!confirmTextView.getText().toString().equals(getString(R.string.email_confirm))){
                    Server.isCondirm(MainActivity.owner.getId());
                    if(MainActivity.owner.isAuth()){
                        confirmTextView.setText(R.string.email_confirm);
                        confirmTextView.setTextColor(Color.GREEN);
                    } else {
                        confirmTextView.setText(R.string.email_not_confirm);
                        confirmTextView.setTextColor(Color.RED);
                    }
                }

                outAccountButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = getActivity().getSharedPreferences(Helper.APP_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove(Helper.APP_PREFERENCES_EMAIL);
                        editor.remove(Helper.APP_PREFERENCES_PASSWORD);
                        editor.apply();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                });
                emailTextView.setText("Email: "+MainActivity.owner.getEmail());
                toRegisterActivityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), RegisterRestaurantActivity.class);
                        startActivity(i);
                    }
                });
            }
        });
        return root;
    }
}