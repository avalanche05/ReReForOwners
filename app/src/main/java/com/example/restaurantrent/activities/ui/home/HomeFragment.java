package com.example.restaurantrent.activities.ui.home;

import android.content.Intent;
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

import com.example.restaurantrent.ActConst;
import com.example.restaurantrent.activities.LoginActivity;
import com.example.restaurantrent.Owner;
import com.example.restaurantrent.R;
import com.example.restaurantrent.activities.MainActivity;
import com.example.restaurantrent.activities.RegisterRestaurantActivity;

import com.example.restaurantrent.services.HttpService;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.ownerInformationTextView);
        final Button toRegisterActivityButton = root.findViewById(R.id.toRegisterActivityButton);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Intent intent = getActivity().getIntent();
                textView.setText(intent.getStringExtra("info"));
                toRegisterActivityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), RegisterRestaurantActivity.class);
                        i.putExtra("idOwner", i.getStringExtra("idOwner"));
                        startActivity(i);
                    }
                });
            }
        });
        return root;
    }
}