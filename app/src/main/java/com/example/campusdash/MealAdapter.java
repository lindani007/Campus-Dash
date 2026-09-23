package com.example.campusdash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> mealList;

    public MealAdapter(List<Meal> mealList) {
        this.mealList = mealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.tvName.setText(meal.getMealname());
        holder.tvDetails.setText("R" + meal.getMealprice() + " • " + meal.getMealcatergory());

        // Glide handles network URLs and drawables smoothly
        Glide.with(holder.itemView.getContext())
                .load(meal.getMeaimage())
                .placeholder(R.drawable.bg_card)
                .error(R.drawable.bg_card)
                .into(holder.imgMeal);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMeal;
        TextView tvName, tvDetails;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            tvName = itemView.findViewById(R.id.tvMealName);
            tvDetails = itemView.findViewById(R.id.tvMealDetails);
        }
    }
}