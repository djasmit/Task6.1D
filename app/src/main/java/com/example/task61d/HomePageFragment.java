package com.example.task61d;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.data.TruckList;
import com.example.task61d.model.Order;
import com.example.task61d.model.Truck;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements HPRecyclerViewAdapter.OnRowClickListener {

    private int _userID;
    private List<Truck> _trucksList;
    private ArrayList<Truck> _trucksArrayList;;
    private RecyclerView _trucksRecyclerView;
    private HPRecyclerViewAdapter _trucksRecyclerViewAdapter;
    private DatabaseHelper db;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _userID = getArguments().getInt("UserID");
        Log.e("Getting to Home", String.valueOf(_userID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment and let us see options menu
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    //inflates the options menu for this fragment
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //refresh database whenever coming to view
        db = new DatabaseHelper(getContext());
        _trucksArrayList = new ArrayList<>();;

        //insert all the trucks on first time setup
        if (db.fetchAllTrucks() == null) {
            Log.e("NoTrucks", "No Trucks");
            TruckList trucklist = new TruckList();
            trucklist.addTrucks();

            for (Truck truck : TruckList.Trucks) {
                Log.e("Truck type", truck.get_truckType());
                Log.e("Truck phone", truck.get_phoneNo());
                db.insertTruck(truck);
            }
        }

        //fetch database of trucks and fill the list with them
        _trucksList = db.fetchAllTrucks();
        _trucksArrayList.addAll(_trucksList);

        //set up user orders recycler view
        _trucksRecyclerView = view.findViewById(R.id.HPFRecyclerView);
        _trucksRecyclerViewAdapter = new HPRecyclerViewAdapter(_trucksArrayList, getActivity(), this::onItemClick);
        _trucksRecyclerView.setAdapter(_trucksRecyclerViewAdapter);
        RecyclerView.LayoutManager HPLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        _trucksRecyclerView.setLayoutManager(HPLayoutManager);

        //floating action button triggers new order menu
        FloatingActionButton NewOrderButton = view.findViewById(R.id.HPFFloatingActionButton);
        NewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new NewDelivery1Fragment();
                selectFragment(fragment, _userID);
            }
        });
    }

    //get the position of the clicked news item and send it to the fragment selector
    @Override
    public void onItemClick(int position) {
        //empty
    }

    //reacts to option being hit
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.OptsMenOrders:

                //display orders if user has any
                if (db.fetchOrder(_userID) == true) {
                    fragment = new UserOrdersFragment();
                    selectFragment(fragment, _userID);
                    return true;
                }

                //no orders, so throw error toast and continue
                Toast.makeText(getContext(), "No orders to view", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //displays a selected fragment
    private void selectFragment(Fragment fragment, int userId) {
        //get the support fragment manager from main activity and initialize a transaction
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create the bundle, fill it with user id, and set it as the fragment argument
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", userId);
        Log.e("Sending from Home", String.valueOf(userId));
        fragment.setArguments(bundle);

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }
}