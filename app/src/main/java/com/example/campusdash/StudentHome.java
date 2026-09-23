package com.example.campusdash;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class StudentHome extends AppCompatActivity {

    private RecyclerView rvMeals;
    private MealAdapter adapter;
    private List<Meal> fullMealList = new ArrayList<>();
    private List<Meal> filteredMealList = new ArrayList<>();

    private LinearLayout emptyView;
    private EditText etBudget, etSearch;
    private Button btnFilterBudget;
    private ChipGroup chipGroupCategories;

    // Navigation Views
    private TextView btnProfile, navBrowse, navCart, navOrders, navProfile;

    private DatabaseHelper dbHelper;
    private String selectedCategory = "All";

    // All categories requested
    private final String[] categories = {
            "All", "Chips", "Grilled Meat", "Hotdogs", "Steamed Bread", "Phuthu", "Rice", "Burgers", "Pizza", "Wraps", "Drinks"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        dbHelper = new DatabaseHelper(this);

        rvMeals = findViewById(R.id.rvMeals);
        emptyView = findViewById(R.id.emptyView);
        etBudget = findViewById(R.id.etBudget);
        etSearch = findViewById(R.id.etSearch);
        btnFilterBudget = findViewById(R.id.btnFilterBudget);
        chipGroupCategories = findViewById(R.id.chipGroupCategories);

        // Header & Bottom Navigation Views
        btnProfile = findViewById(R.id.btnProfile);
        navBrowse = findViewById(R.id.navBrowse);
        navCart = findViewById(R.id.navCart);
        navOrders = findViewById(R.id.navOrders);
        navProfile = findViewById(R.id.navProfile);

        rvMeals.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MealAdapter(filteredMealList);
        rvMeals.setAdapter(adapter);

        setupCategories();
        loadMealsFromDB();

        // Budget Filter Action
        btnFilterBudget.setOnClickListener(v -> applyFilters());

        // Live Search Filter
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up Bottom Navigation & Header Profile Listeners
        setupNavigation();
    }

    private void setupNavigation() {
        // Header Profile Icon -> ProfileActivity
        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                startActivity(new Intent(StudentHome.this, ProfileActivity.class));
            });
        }

        // Bottom Bar: Browse -> Set focus to search input
        if (navBrowse != null) {
            navBrowse.setOnClickListener(v -> {
                startActivity(new Intent(StudentHome.this, BrowseActivity.class));
            });
        }

        // Bottom Bar: Cart -> CartActivity
        if (navCart != null) {
            navCart.setOnClickListener(v -> {
                startActivity(new Intent(StudentHome.this, CartActivity.class));
            });
        }

        // Bottom Bar: Orders -> OrdersActivity
        if (navOrders != null) {
            navOrders.setOnClickListener(v -> {
                startActivity(new Intent(StudentHome.this, OrdersActivity.class));
            });
        }

        // Bottom Bar: Profile -> ProfileActivity
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                startActivity(new Intent(StudentHome.this, ProfileActivity.class));
            });
        }
    }

    private void setupCategories() {
        chipGroupCategories.removeAllViews();

        for (String cat : categories) {
            Chip chip = new Chip(this);
            chip.setText(cat);
            chip.setCheckable(true);
            if (cat.equals("All")) {
                chip.setChecked(true);
            }

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedCategory = cat;
                    applyFilters();
                }
            });

            chipGroupCategories.addView(chip);
        }
    }

    private void loadMealsFromDB() {
        fullMealList = dbHelper.getAllMeals();
        applyFilters();
    }

    private void applyFilters() {
        filteredMealList.clear();

        String searchQuery = etSearch.getText().toString().trim().toLowerCase();
        String budgetStr = etBudget.getText().toString().trim();

        double maxBudget = Double.MAX_VALUE;
        if (!budgetStr.isEmpty()) {
            try {
                maxBudget = Double.parseDouble(budgetStr);
            } catch (NumberFormatException ignored) {}
        }

        for (Meal meal : fullMealList) {
            boolean matchesCategory = selectedCategory.equals("All") ||
                    meal.getMealcatergory().equalsIgnoreCase(selectedCategory);

            boolean matchesSearch = meal.getMealname().toLowerCase().contains(searchQuery);

            boolean matchesBudget = meal.getMealprice() <= maxBudget;

            if (matchesCategory && matchesSearch && matchesBudget) {
                filteredMealList.add(meal);
            }
        }

        adapter.notifyDataSetChanged();

        // Show empty view if no meals match the criteria
        if (filteredMealList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            rvMeals.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            rvMeals.setVisibility(View.VISIBLE);
        }
    }
}