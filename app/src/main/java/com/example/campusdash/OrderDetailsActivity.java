package com.example.campusdash;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView tvOrderTitle, tvOrderTotal, btnBack;
    private RecyclerView recyclerViewOrderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        dbHelper = new DatabaseHelper(this);

        // Bind Back Button & Set Click Listener
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish()); // Closes current activity and goes back
        }

        tvOrderTitle = findViewById(R.id.tvOrderTitle);
        tvOrderTotal = findViewById(R.id.tvOrderTotal);
        recyclerViewOrderItems = findViewById(R.id.recyclerViewOrderItems);
        recyclerViewOrderItems.setLayoutManager(new LinearLayoutManager(this));

        int orderId = getIntent().getIntExtra("ORDER_ID", -1);
        String orderNum = getIntent().getStringExtra("ORDER_NUM");
        double total = getIntent().getDoubleExtra("TOTAL", 0.0);

        tvOrderTitle.setText("Order #" + orderNum);
        tvOrderTotal.setText("Total Amount: R" + String.format("%.2f", total));

        loadOrderItems(orderId);
    }

    private void loadOrderItems(int orderId) {
        List<CartItem> itemsList = new ArrayList<>();
        Cursor cursor = dbHelper.getOrderItems(orderId);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("meal_name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("meal_price"));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                itemsList.add(new CartItem(0, 0, name, price, "", qty));
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Re-use CartAdapter in read-only mode to display items
        CartAdapter adapter = new CartAdapter(itemsList, (item, newQty) -> {});
        recyclerViewOrderItems.setAdapter(adapter);
    }
}