package com.example.campusdash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeliveryGuySimActivity extends AppCompatActivity {

    private TextView tvAvailableOrder, btnBack;
    private Button btnAcceptOrder, btnCompleteDelivery;
    private DatabaseHelper dbHelper;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_guy_sim);

        dbHelper = new DatabaseHelper(this);
        orderId = getIntent().getStringExtra("ORDER_ID");

        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        tvAvailableOrder = findViewById(R.id.tvAvailableOrder);
        btnAcceptOrder = findViewById(R.id.btnAcceptOrder);
        btnCompleteDelivery = findViewById(R.id.btnCompleteDelivery);

        if (orderId != null) {
            tvAvailableOrder.setText("New Request: Order #" + orderId + " is ready at Campus Canteen.");
        }

        btnAcceptOrder.setOnClickListener(v -> {
            dbHelper.updateOrderStatus(orderId, "Out for Delivery");
            Toast.makeText(this, "Order accepted! En route to customer.", Toast.LENGTH_SHORT).show();
            btnAcceptOrder.setEnabled(false);
            btnCompleteDelivery.setEnabled(true);
        });

        btnCompleteDelivery.setOnClickListener(v -> {
            dbHelper.updateOrderStatus(orderId, "Delivered");
            Toast.makeText(this, "Order delivered successfully!", Toast.LENGTH_LONG).show();
            finish();
        });
    }
}