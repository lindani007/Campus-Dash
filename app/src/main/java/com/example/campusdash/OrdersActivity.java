package com.example.campusdash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewOrders;
    private List<Order> orderList;
    private TextView btnBack; // Declared at class level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders2);

        dbHelper = new DatabaseHelper(this);

        // Bind and set click listener inside onCreate
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish()); // Closes current activity and goes back
        }

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        loadOrders();
    }

    private void loadOrders() {
        orderList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllOrders();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String orderId = cursor.getString(cursor.getColumnIndexOrThrow("orderid"));
                int mealId = cursor.getInt(cursor.getColumnIndexOrThrow("mealid"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("userEmail"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("orderstatus"));

                orderList.add(new Order(orderId, mealId, quantity, date, location, userEmail, status));
            } while (cursor.moveToNext());
            cursor.close();
        }

        OrdersAdapter adapter = new OrdersAdapter(orderList, order -> {
            Intent intent = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
            intent.putExtra("ORDER_ID", order.getOrderid());
            intent.putExtra("MEAL_ID", order.getMealid());
            intent.putExtra("QTY", order.getQuantity());
            intent.putExtra("LOCATION", order.getLocation());
            startActivity(intent);
        });

        recyclerViewOrders.setAdapter(adapter);
    }
}