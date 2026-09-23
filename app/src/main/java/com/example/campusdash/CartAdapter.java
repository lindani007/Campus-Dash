package com.example.campusdash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartChangeListener {
        void QtyChanged(CartItem item, int newQty);
    }

    private List<CartItem> cartList;
    private OnCartChangeListener listener;

    public CartAdapter(List<CartItem> cartList, OnCartChangeListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);
        holder.tvMealName.setText(item.getMealName());
        holder.tvMealPrice.setText("R" + String.format("%.2f", item.getMealPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnPlus.setOnClickListener(v -> {
            if (listener != null) listener.QtyChanged(item, item.getQuantity() + 1);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (listener != null) listener.QtyChanged(item, item.getQuantity() - 1);
        });
    }

    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealName, tvMealPrice, tvQuantity;
        Button btnPlus, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvCartMealName);
            tvMealPrice = itemView.findViewById(R.id.tvCartMealPrice);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}