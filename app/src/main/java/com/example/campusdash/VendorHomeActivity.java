package com.example.campusdash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class VendorHomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView btnOpenDrawer, tvStatRevenue, tvStatDrivers, tvStatMeals;
    private Button btnQuickAddMeal;

    // Drawer buttons
    private Button navManageMenu, navManageDrivers, navActiveOrders, navOrderHistory, navLogout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_home);

        dbHelper = new DatabaseHelper(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        btnOpenDrawer = findViewById(R.id.btnOpenDrawer);

        tvStatRevenue = findViewById(R.id.tvStatRevenue);
        tvStatDrivers = findViewById(R.id.tvStatDrivers);
        tvStatMeals = findViewById(R.id.tvStatMeals);
        btnQuickAddMeal = findViewById(R.id.btnQuickAddMeal);

        navManageMenu = findViewById(R.id.navManageMenu);
        navManageDrivers = findViewById(R.id.navManageDrivers);
        navActiveOrders = findViewById(R.id.navActiveOrders);
        navOrderHistory = findViewById(R.id.navOrderHistory);
        navLogout = findViewById(R.id.navLogout);

        // Open Flyout Drawer
        if (btnOpenDrawer != null && drawerLayout != null) {
            btnOpenDrawer.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        // Navigation Actions
        if (btnQuickAddMeal != null) {
            btnQuickAddMeal.setOnClickListener(v -> startActivity(new Intent(this, VendorMenuActivity.class)));
        }

        if (navManageMenu != null) {
            navManageMenu.setOnClickListener(v -> {
                closeDrawerSafely();
                startActivity(new Intent(this, VendorMealActivity.class));
            });
        }

        if (navManageDrivers != null) {
            navManageDrivers.setOnClickListener(v -> {
                closeDrawerSafely();
                startActivity(new Intent(this, VendorDeliveryGuysActivity.class));
            });
        }

        if (navActiveOrders != null) {
            navActiveOrders.setOnClickListener(v -> {
                closeDrawerSafely();
                startActivity(new Intent(this, VendorOrdersActivity.class));
            });
        }

        if (navOrderHistory != null) {
            navOrderHistory.setOnClickListener(v -> {
                closeDrawerSafely();
                startActivity(new Intent(this, OrdersActivity.class));
            });
        }

        if (navLogout != null) {
            navLogout.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }

    private void closeDrawerSafely() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboardStats();
    }

    private void loadDashboardStats() {
        try {
            // 1. Total Meals Count
            if (dbHelper.getAllMeals() != null) {
                int mealCount = dbHelper.getAllMeals().size();
                tvStatMeals.setText(String.valueOf(mealCount));
            }

            // 2. Delivery Drivers Count
            if (dbHelper.getAllDeliveryGuys() != null) {
                int driverCount = dbHelper.getAllDeliveryGuys().size();
                tvStatDrivers.setText(String.valueOf(driverCount));
            }

            // 3. Today's Total Revenue Calculation
            double totalRevenue = 0.0;
            Cursor cursor = dbHelper.getAllOrders();
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    // Try to get "total" column, or fallback to index 3/4 if column name differs
                    int totalIndex = cursor.getColumnIndex("total");
                    if (totalIndex == -1) {
                        totalIndex = cursor.getColumnIndex("totalamount");
                    }

                    if (totalIndex != -1) {
                        do {
                            totalRevenue += cursor.getDouble(totalIndex);
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }
            tvStatRevenue.setText("R" + String.format("%.2f", totalRevenue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}