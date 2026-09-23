package com.example.campusdash;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class VendorMenuActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView tvStoreTitle;
    private RecyclerView recyclerViewVendorMeals;
    private List<Meal> fullMealList;
    private List<Meal> displayedMealList;
    private int currentStoreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_menu);

        dbHelper = new DatabaseHelper(this);

        tvStoreTitle = findViewById(R.id.tvStoreTitle);
        recyclerViewVendorMeals = findViewById(R.id.recyclerViewVendorMeals);
        recyclerViewVendorMeals.setLayoutManager(new LinearLayoutManager(this));

        // Get Store details passed from BrowseActivity
        currentStoreId = getIntent().getIntExtra("STORE_ID", -1);
        String storeName = getIntent().getStringExtra("STORE_NAME");

        if (storeName != null) {
            tvStoreTitle.setText(storeName);
        }

        // Fetch meals belonging specifically to this store
        loadVendorMeals();
    }

    private void loadVendorMeals() {
        fullMealList = dbHelper.getMealsByStore(currentStoreId);

        // Fallback: If no meals are linked to this specific store yet, fetch all meals
        if (fullMealList == null || fullMealList.isEmpty()) {
            fullMealList = dbHelper.getAllMeals();
        }

        displayedMealList = new ArrayList<>(fullMealList);

    }
}