package com.example.campusdash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    private List<Order> orderList;
    private OnOrderClickListener listener;

    public OrdersAdapter(List<Order> orderList, OnOrderClickListener listener) {
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_card, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderNumber.setText("#" + order.getOrderid());
        holder.tvOrderVendor.setText("Deliver to: " + order.getLocation());
        holder.tvOrderStatus.setText("Status: " + order.getOrderstatus());
        holder.tvOrderAmount.setText("Qty: " + order.getQuantity());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onOrderClick(order);
        });
    }

    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size() : 0;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNumber, tvOrderVendor, tvOrderStatus, tvOrderAmount;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNumber = itemView.findViewById(R.id.tvOrderNumber);
            tvOrderVendor = itemView.findViewById(R.id.tvOrderVendor);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderAmount = itemView.findViewById(R.id.tvOrderAmount);
        }
    }
}