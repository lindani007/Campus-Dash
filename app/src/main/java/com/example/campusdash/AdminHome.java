package com.example.campusdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AdminHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ImageButton btnMenu;
    private TextView tvVendorsCount, tvOrdersCount, tvPaymentsCount, tvActiveVendorsCount;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        dbHelper = new DatabaseHelper(this);

        // Bind layout views matching XML IDs
        drawerLayout = findViewById(R.id.admin_drawer);
        btnMenu = findViewById(R.id.btn_menu);

        tvVendorsCount = findViewById(R.id.tv_vendors_count);
        tvOrdersCount = findViewById(R.id.tv_orders_count);
        tvPaymentsCount = findViewById(R.id.tv_payments_count);
        tvActiveVendorsCount = findViewById(R.id.tv_active_vendors_count);

        NavigationView navigationView = findViewById(R.id.admin_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Open menu drawer on menu icon click
        btnMenu.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Load live dashboard metrics from database
        loadDashboardData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh counts whenever returning to AdminHome
        loadDashboardData();
    }

    private void loadDashboardData() {
        int vendorsCount = dbHelper.getVendorsCount();
        int ordersCount = dbHelper.getOrdersCount();
        int paymentsCount = dbHelper.getPaymentsCount();
        int activeVendorsCount = dbHelper.getActiveVendorsCount();

        tvVendorsCount.setText(String.valueOf(vendorsCount));
        tvOrdersCount.setText(String.valueOf(ordersCount));
        tvPaymentsCount.setText(String.valueOf(paymentsCount));
        tvActiveVendorsCount.setText(String.valueOf(activeVendorsCount));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Already on dashboard
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.nav_vendors) {
            Intent intent = new Intent(AdminHome.this, VendorManagementActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order_history) {
            Intent intent = new Intent(AdminHome.this, Orders.class);
            startActivity(intent);
        } else if (id == R.id.nav_payment_history) {
            Intent intent = new Intent(AdminHome.this, PaymentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_reports) {
            Toast.makeText(this, "Reports Selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(AdminHome.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}