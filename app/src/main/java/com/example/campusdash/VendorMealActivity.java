package com.example.campusdash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class VendorMealActivity extends AppCompatActivity {

    private EditText etMealName, etMealPrice, etCategory, etImageUrl;
    private Button btnAddMeal;
    private TextView btnBack;
    private RecyclerView rvVendorMeals;
    private DatabaseHelper dbHelper;
    private VendorMealAdapter adapter;
    private List<Meal> mealList;
    private int selectedMealId = -1; // -1 means adding new meal; positive number means editing existing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_meal);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        etMealName = findViewById(R.id.etMealName);
        etMealPrice = findViewById(R.id.etMealPrice);
        etCategory = findViewById(R.id.etCategory);
        etImageUrl = findViewById(R.id.etImageUrl);
        btnAddMeal = findViewById(R.id.btnAddMeal);

        rvVendorMeals = findViewById(R.id.rvVendorMeals);
        rvVendorMeals.setLayoutManager(new LinearLayoutManager(this));

        btnAddMeal.setOnClickListener(v -> saveOrUpdateMeal());

        loadMealsList();
    }

    private void loadMealsList() {
        mealList = dbHelper.getAllMeals();
        adapter = new VendorMealAdapter(mealList);
        rvVendorMeals.setAdapter(adapter);
    }

    private void saveOrUpdateMeal() {
        String name = etMealName.getText().toString().trim();
        String priceStr = etMealPrice.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String imageUrl = etImageUrl.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        if (selectedMealId == -1) {
            // INSERT NEW MEAL
            boolean success = dbHelper.insertMeal(name, price, category, imageUrl);
            if (success) {
                Toast.makeText(this, "Meal added successfully!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // UPDATE EXISTING MEAL
            boolean success = dbHelper.updateMeal(selectedMealId, name, price, category, imageUrl);
            if (success) {
                Toast.makeText(this, "Meal updated successfully!", Toast.LENGTH_SHORT).show();
                selectedMealId = -1;
                btnAddMeal.setText("Add Meal");
            }
        }

        clearFields();
        loadMealsList(); // Refresh RecyclerView
    }

    private void clearFields() {
        etMealName.setText("");
        etMealPrice.setText("");
        etCategory.setText("");
        etImageUrl.setText("");
    }

    // --- INNER ADAPTER FOR VENDOR MEAL LIST ---
    private class VendorMealAdapter extends RecyclerView.Adapter<VendorMealAdapter.MealViewHolder> {

        private final List<Meal> meals;

        VendorMealAdapter(List<Meal> meals) {
            this.meals = meals;
        }

        @NonNull
        @Override
        public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vendor_meal, parent, false);
            return new MealViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
            Meal meal = meals.get(position);
            holder.tvMealName.setText(meal.getMealname());
            holder.tvMealDetails.setText("R" + String.format("%.2f", meal.getMealprice()) + " | " + meal.getMealcatergory());

            // Edit button populates fields for editing
            holder.btnEditMeal.setOnClickListener(v -> {
                selectedMealId = meal.getMealid();
                etMealName.setText(meal.getMealname());
                etMealPrice.setText(String.valueOf(meal.getMealprice()));
                etCategory.setText(meal.getMealcatergory());
                etImageUrl.setText(meal.getMeaimage());
                btnAddMeal.setText("Update Meal");
            });

            // Delete button removes meal from SQLite
            holder.btnDeleteMeal.setOnClickListener(v -> {
                dbHelper.deleteMeal(meal.getMealid());
                Toast.makeText(VendorMealActivity.this, "Meal deleted", Toast.LENGTH_SHORT).show();
                loadMealsList();
            });
        }

        @Override
        public int getItemCount() {
            return meals.size();
        }

        class MealViewHolder extends RecyclerView.ViewHolder {
            TextView tvMealName, tvMealDetails;
            Button btnEditMeal, btnDeleteMeal;

            MealViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMealName = itemView.findViewById(R.id.tvMealName);
                tvMealDetails = itemView.findViewById(R.id.tvMealDetails);
                btnEditMeal = itemView.findViewById(R.id.btnEditMeal);
                btnDeleteMeal = itemView.findViewById(R.id.btnDeleteMeal);
            }
        }
    }
}