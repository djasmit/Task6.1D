package com.example.task61d;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.model.Order;
import com.example.task61d.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserOrdersFragment extends Fragment implements UORecyclerViewAdapter.OnRowClickListener {

    private int _userID;
    private List<Order> _userOrdersList;
    private ArrayList<Order> _orderArrayList;;
    private RecyclerView _userOrderRecyclerView;
    private UORecyclerViewAdapter _UORecyclerViewAdapter;
    private DatabaseHelper db;

    public UserOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _userID = getArguments().getInt("UserID");
        Log.e("Getting to UOrders", String.valueOf(_userID));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment and let us see options menu
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_user_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(getContext());
        _orderArrayList = new ArrayList<>();;

        //fetch collection of orders from database and fill array list with them
        _userOrdersList = db.fetchAllOrdersFrom(_userID);
        Log.e("Order ID", String.valueOf(_userOrdersList.get(0).get_orderID()));
        Log.e("Sender ID", String.valueOf(_userOrdersList.get(0).get_senderID()));
        Log.e("receiver ID", String.valueOf(_userOrdersList.get(0).get_receiverID()));
        _orderArrayList.addAll(_userOrdersList);

        //set up user orders recycler view
        _userOrderRecyclerView = view.findViewById(R.id.UOFRecyclerView);
        _UORecyclerViewAdapter = new UORecyclerViewAdapter(_orderArrayList, getActivity(), this::onItemClick);
        _userOrderRecyclerView.setAdapter(_UORecyclerViewAdapter);
        RecyclerView.LayoutManager UOLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        _userOrderRecyclerView.setLayoutManager(UOLayoutManager);

        //button to select new order
        FloatingActionButton NewOrderButton = view.findViewById(R.id.UOFFloatingActionButton);
        NewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new NewDelivery1Fragment();
                selectFragment(fragment, _userID);
            }
        });
    }

    //inflates the options menu for this fragment
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //reacts to option being hit
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.OptsMenHome:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //get the position of the clicked news item and send it to the fragment selector
    @Override public void onItemClick(int position) {
        int orderID = _orderArrayList.get(position).get_orderID();
        displayOrder(orderID);
    }

    //displays a selected fragment
    private void displayOrder(int orderID) {
        //get the support fragment manager from main activity and initialize a transaction
        Fragment fragment = new DisplayOrderFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create the bundle, fill it with user id, and set it as the fragment argument
        Bundle bundle = new Bundle();
        bundle.putInt("OrderID", orderID);
        fragment.setArguments(bundle);

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }

    //displays a selected fragment
    private void selectFragment(Fragment fragment, int userID) {
        //get the support fragment manager from main activity and initialize a transaction
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create the bundle, fill it with user id, and set it as the fragment argument
        Bundle bundle = new Bundle();
        bundle.putInt("userID", userID);
        Log.e("Sending from Orders", String.valueOf(userID));
        fragment.setArguments(bundle);

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }
}