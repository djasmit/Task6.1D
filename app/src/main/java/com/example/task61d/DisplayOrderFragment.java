package com.example.task61d;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.task61d.data.DatabaseHelper;
import com.example.task61d.model.Order;
import com.example.task61d.model.User;

public class DisplayOrderFragment extends Fragment {

    private int _orderID;
    private DatabaseHelper db;

    public DisplayOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _orderID = getArguments().getInt("OrderID");
        db = new DatabaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get our selected order from database
        Order currentOrder = db.getOrder(_orderID);
        String weight = currentOrder.get_weight();
        String height = currentOrder.get_height();
        String length = currentOrder.get_length();
        String width = currentOrder.get_width();
        String type = currentOrder.get_goodType();

        //get sender and receiver
        int fromID = currentOrder.get_senderID();
        int toID = currentOrder.get_receiverID();
        User fromUser = db.getUser(fromID);
        User toUser = db.getUser(toID);
        String fromString = fromUser.get_username();
        String toString = toUser.get_username();

        ///get text views
        TextView weightTextView = view.findViewById(R.id.DOFWeightValueTextView);
        TextView heightTextView = view.findViewById(R.id.DOFHeightValueTextView);
        TextView lengthTextView = view.findViewById(R.id.DOFLengthValueTextView);
        TextView widthTextView = view.findViewById(R.id.DOFWidthValueTextView);
        TextView typeTextView = view.findViewById(R.id.DOFTypeValueTextView);
        TextView fromTextView = view.findViewById(R.id.DOFFromTextView);
        TextView toTextView = view.findViewById(R.id.DOFToTextView);

        //set textview outputs to current order values
        weightTextView.setText(weight + "(kg)");
        heightTextView.setText(height + "(m)");
        lengthTextView.setText(length + "(m)");
        widthTextView.setText(width + "(m)");
        typeTextView.setText(type);
        fromTextView.setText("From: " + fromString);
        toTextView.setText("To: " + toString);

    }
}