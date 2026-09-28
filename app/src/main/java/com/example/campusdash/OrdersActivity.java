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
    private TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders2);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
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
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String orderNumber = cursor.getString(cursor.getColumnIndexOrThrow("order_number"));
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow("student_name"));
                String vendorName = cursor.getString(cursor.getColumnIndexOrThrow("vendor_name"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                orderList.add(new Order(id, orderNumber, studentName, vendorName, amount, status));
            } while (cursor.moveToNext());
            cursor.close();
        }

        OrdersAdapter adapter = new OrdersAdapter(orderList, order -> {
            Intent intent = new Intent(OrdersActivity.this, OrderDetailsActivity.class);
            intent.putExtra("ORDER_ID", order.getId());
            intent.putExtra("ORDER_NUM", order.getOrderNumber());
            intent.putExtra("TOTAL", order.getAmount());
            startActivity(intent);
        });

        recyclerViewOrders.setAdapter(adapter);
    }
}
