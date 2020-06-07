package com.example.restaurantrent_owner.activities.ui.notifications;

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
import com.example.restaurantrent_owner.activities.ViewRentTablesActivity;
import com.example.restaurantrent_owner.adapters.RentAdapter;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final ListView rentsList = root.findViewById(R.id.rentsListView);
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Server.getOwnerRent(MainActivity.owner.getId());
                RentAdapter rentAdapter = new RentAdapter(getContext(),MainActivity.rents);
                rentsList.setAdapter(rentAdapter);
                rentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        long restaurantId = MainActivity.rents.get(position).getIdRestaurant();
                        ViewRentTablesActivity.index = position;
                        Server.tableGet(restaurantId,getActivity(),new Intent(getActivity(), ViewRentTablesActivity.class));
                    }
                });
            }
        });
        return root;
    }
}