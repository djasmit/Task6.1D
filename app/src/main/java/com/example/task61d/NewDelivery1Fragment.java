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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewDelivery1Fragment extends Fragment {

    private int _userID;
    private List<User> userList;
    private ArrayList<User> userArrayList;
    private ArrayAdapter<User> userArrayAdapter;
    private DatabaseHelper db;
    private String _orderDate;
    private Spinner ReceiverSpinner;

    public NewDelivery1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _userID = getArguments().getInt("UserID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_delivery1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DatabaseHelper(getContext());

        //get users from database and set spinner to use them
        ReceiverSpinner = view.findViewById(R.id.ND1FReceiverSpinner);
        userArrayList = new ArrayList<>();
        userList = db.fetchAllUsers();
        userArrayList.addAll(userList);
        userArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, userArrayList);
        ReceiverSpinner.setAdapter(userArrayAdapter);

        //initialize calender and make sure it can't select dates before today
        CalendarView deliveryCalender = view.findViewById(R.id.ND1FCalendarView);
        deliveryCalender.setMinDate((new Date().getTime()));
        deliveryCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                _orderDate = day + "/" + month + "/" + year;
            }
        });

        //get time and location fields
        EditText orderTimeEditText = view.findViewById(R.id.ND1FTimeEditText);
        EditText orderLocEditText = view.findViewById(R.id.ND1FLocEditText);

        //set actions for when next button is clicked
        Button nextStageButton = view.findViewById(R.id.ND1FNextButton);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderTimeString = orderTimeEditText.getText().toString();
                String orderLocString = orderLocEditText.getText().toString();
                int position = ReceiverSpinner.getSelectedItemPosition();
                int receiverId = userArrayList.get(position).get_userId();

                nextOrderStage(_userID, receiverId, _orderDate, orderTimeString, orderLocString);
            }
        });
    }

    //displays a selected fragment
    private void nextOrderStage(int userID, int receiverID, String orderDate, String orderTime, String orderLoc) {
        //get the support fragment manager from main activity and initialize a transaction
        Fragment fragment = new NewDelivery2Fragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //create the bundle, fill it with user id, and set it as the fragment argument
        Bundle bundle = new Bundle();
        bundle.putInt("UserID", userID);
        bundle.putInt("ReceiverID", receiverID);
        bundle.putString("OrderDate", orderDate);
        bundle.putString("OrderTime", orderTime);
        bundle.putString("OrderLocation", orderLoc);
        fragment.setArguments(bundle);

        //replace current fragment in view with selected fragment and add it to backstack
        fragmentTransaction.replace(R.id.MainActivityFragmentView, fragment).addToBackStack(null).commit();
    }
}