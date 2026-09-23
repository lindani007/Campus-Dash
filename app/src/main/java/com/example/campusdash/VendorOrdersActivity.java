package com.example.campusdash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VendorOrdersActivity extends AppCompatActivity {

    private Button btnMarkPreparing, btnCallDelivery;
    private TextView tvOrderStatus, btnBack;
    private DatabaseHelper dbHelper;
    private String currentOrderId = "ORD-1001"; // Simulated active order ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_orders);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        btnMarkPreparing = findViewById(R.id.btnMarkPreparing);
        btnCallDelivery = findViewById(R.id.btnCallDelivery);

        btnMarkPreparing.setOnClickListener(v -> {
            dbHelper.updateOrderStatus(currentOrderId, "Preparing");
            tvOrderStatus.setText("Status: Preparing");
            Toast.makeText(this, "Order marked as Preparing", Toast.LENGTH_SHORT).show();
        });

        // Simulates dispatching the job to drivers
        btnCallDelivery.setOnClickListener(v -> {
            dbHelper.updateOrderStatus(currentOrderId, "Ready for Pickup");
            tvOrderStatus.setText("Status: Ready for Pickup");
            Toast.makeText(this, "Notification sent to delivery drivers!", Toast.LENGTH_LONG).show();

            // Launch simulated driver view
            Intent intent = new Intent(this, DeliveryGuySimActivity.class);
            intent.putExtra("ORDER_ID", currentOrderId);
            startActivity(intent);
        });
    }
}