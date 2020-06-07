package com.example.restaurantrent_owner.activities.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantrent_owner.R;

import com.example.restaurantrent_owner.Server;
import com.example.restaurantrent_owner.activities.MainActivity;

import com.example.restaurantrent_owner.activities.ViewTablesActivity;
import com.example.restaurantrent_owner.adapters.RestaurantAdapter;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ListView restaurantList = root.findViewById(R.id.restaurntList);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                Server.getRestaurants(MainActivity.owner.getId());

                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getContext(),MainActivity.restaurants);
                restaurantList.setAdapter(restaurantAdapter);
                restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        long restaurantId = MainActivity.restaurants.get(position).getId();
                        ViewTablesActivity.index = position;
                        Server.tableGet(restaurantId,getActivity(),new Intent(getActivity(), ViewTablesActivity.class));
                    }
                });
            }
        });
        return root;
        }
    }
