package com.example.task61d;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.model.Order;

public class NewDelivery2Fragment extends Fragment {

    private int _userID;
    private int _receiveID;
    private String _orderDate;
    private String _orderTime;
    private String _orderLoc;

    private DatabaseHelper db;

    public NewDelivery2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _userID = getArguments().getInt("UserID");
        _receiveID = getArguments().getInt("ReceiverID");
        _orderDate = getArguments().getString("OrderDate");
        _orderTime = getArguments().getString("OrderTime");
        _orderLoc = getArguments().getString("OrderLocation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_delivery2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(getContext());

        //package measurements
        EditText goodTypeEditText = view.findViewById(R.id.NDF2GoodTypeEditText);
        EditText weightEditText = view.findViewById(R.id.ND2FWeightEditText);
        EditText lengthEditText = view.findViewById(R.id.ND2FLengthEditText);
        EditText widthEditText = view.findViewById(R.id.ND2FWidthEditText);
        EditText heightEditText = view.findViewById(R.id.ND2FHeightEditText);
        EditText truckTypeEditText = view.findViewById(R.id.NDF2TruckTypeEditText);

        //set actions for when next button is clicked
        Button createOrderButton = view.findViewById(R.id.ND2FCreateOrderButton);
        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String goodTypeString = goodTypeEditText.getText().toString();
                String weightString = weightEditText.getText().toString();
                String lengthString = lengthEditText.getText().toString();
                String widthString = widthEditText.getText().toString();
                String heightString = heightEditText.getText().toString();
                String truckTypeString = truckTypeEditText.getText().toString();

                Order order = new Order(_userID, _receiveID, _orderDate, _orderTime, _orderLoc, goodTypeString, weightString, heightString, widthString, lengthString, truckTypeString);
                long result = db.insertOrder(order);

                //fail if order wasn't entered successfully
                if (result <= 0) {
                    Toast.makeText(getContext(), "Failed to register order!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //user entered successfully, so set toast and leave this fragment
                Toast.makeText(getContext(), "Registered advert successfully!", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    //displays a selected fragment
    private void returnHome(int userId) {
        //get the support fragment manager from main activity and initialize a transaction
        Fragment fragment = new HomePageFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create the bundle, fill it with user id, and set it as the fragment argument
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", userId);
        fragment.setArguments(bundle);

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }
}