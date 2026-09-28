package com.example.campusdash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

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
        holder.tvOrderNumber.setText("Order #" + order.getOrderNumber());
        holder.tvOrderVendor.setText("Vendor: " + order.getVendorName());
        holder.tvOrderStatus.setText("Status: " + order.getStatus());
        holder.tvOrderAmount.setText(String.format(Locale.getDefault(), "R%.2f", order.getAmount()));

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
