package com.example.campusdash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    public interface OnStoreClickListener {
        void onStoreClick(Store store);
    }

    private List<Store> storeList;
    private OnStoreClickListener listener;

    public StoreAdapter(List<Store> storeList, OnStoreClickListener listener) {
        this.storeList = storeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_card, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.tvStoreName.setText(store.getStorename());
        holder.tvVendorName.setText(store.getVendorname());
        holder.tvRating.setText("★ " + (store.getRating() != null ? store.getRating() : "5.0"));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStoreClick(store);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeList != null ? storeList.size() : 0;
    }

    static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView tvStoreName, tvVendorName, tvRating;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            tvVendorName = itemView.findViewById(R.id.tvVendorName);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
