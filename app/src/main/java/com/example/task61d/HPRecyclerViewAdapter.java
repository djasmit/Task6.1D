package com.example.task61d;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task61d.model.Order;
import com.example.task61d.model.Truck;

import java.util.List;

public class HPRecyclerViewAdapter extends RecyclerView.Adapter<HPRecyclerViewAdapter.ViewHolder> {
    private List<Truck> _truckList;
    private Context _context;
    private OnRowClickListener _listener;

    public HPRecyclerViewAdapter(List<Truck> _truckList, Context _context, OnRowClickListener _listener) {
        this._truckList = _truckList;
        this._context = _context;
        this._listener = _listener;
    }

    @NonNull
    @Override
    public HPRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(_context).inflate(R.layout.truck_list_layout, parent, false);
        return new ViewHolder(itemView, _listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HPRecyclerViewAdapter.ViewHolder holder, int position) {
        Truck currentTruck = _truckList.get(position);
        String truckType = String.valueOf(currentTruck.get_truckType());

        holder._truckType.setText(truckType);
        holder._truckPhone.setText(currentTruck.get_phoneNo());

        holder._shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                String body = "Call " + currentTruck.get_phoneNo() + " to get deliveries from this " + truckType;
                String sub = "Truck Found on TruckShare";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                _context.startActivity(Intent.createChooser(shareIntent, "Share Using"));
            }
        });
    }

    @Override
    public int getItemCount() { return _truckList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView _truckType;
        TextView _truckPhone;
        OnRowClickListener _onRowClickListener;
        Button _shareButton;

        public ViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            _onRowClickListener = onRowClickListener;
            _truckType = itemView.findViewById(R.id.TruckTypeTextView);
            _truckPhone = itemView.findViewById(R.id.TruckPhoneNoTextView);
            _shareButton = itemView.findViewById(R.id.TruckShareButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            _onRowClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnRowClickListener {
        void onItemClick(int position);
    }
}
