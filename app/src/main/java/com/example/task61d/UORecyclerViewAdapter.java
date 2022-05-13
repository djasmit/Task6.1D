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

import java.util.List;

public class UORecyclerViewAdapter extends RecyclerView.Adapter<UORecyclerViewAdapter.ViewHolder> {
    private List<Order> _orderList;
    private Context _context;
    private OnRowClickListener _listener;

    public UORecyclerViewAdapter(List<Order> _orderList, Context _context, OnRowClickListener _listener) {
        this._orderList = _orderList;
        this._context = _context;
        this._listener = _listener;
    }

    @NonNull @Override
    public UORecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(_context).inflate(R.layout.order_user_list_layout, parent, false);
        return new ViewHolder(itemView, _listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UORecyclerViewAdapter.ViewHolder holder, int position) {
        Log.e("BindVH Position", String.valueOf(position));
        Order currentOrder = _orderList.get(position);
        String receiverIDString = String.valueOf(currentOrder.get_receiverID());
        holder._orderReceiverTextView.setText(receiverIDString);
        holder._goodTypeTextView.setText(currentOrder.get_goodType());

        holder._shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                String body = "Sending order to " + receiverIDString;
                String sub = "Sending order";
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                _context.startActivity(Intent.createChooser(shareIntent, "Share Using"));
            }
        });
    }

    @Override
    public int getItemCount() { return _orderList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView _goodTypeTextView;
        TextView _orderReceiverTextView;
        OnRowClickListener _onRowClickListener;
        Button _shareButton;

        public ViewHolder(@NonNull View itemView, OnRowClickListener onRowClickListener) {
            super(itemView);
            _onRowClickListener = onRowClickListener;
            _goodTypeTextView = itemView.findViewById(R.id.userOrderGoodTypeTextView);
            _orderReceiverTextView = itemView.findViewById(R.id.userOrderReceiverTextView);
            _shareButton = itemView.findViewById(R.id.userOrderShareButton);

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
