package com.example.campusdash;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {

    private ListView lvOrders;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private OrderAdapter adapter;
    private List<OrderItem> orderList;

    public static class OrderItem {
        String orderNumber;
        String studentName;
        String vendorName;
        double amount;
        String status;

        public OrderItem(String orderNumber, String studentName, String vendorName, double amount, String status) {
            this.orderNumber = orderNumber;
            this.studentName = studentName;
            this.vendorName = vendorName;
            this.amount = amount;
            this.status = status;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        lvOrders = findViewById(R.id.lv_orders);
        btnBack = findViewById(R.id.btn_back);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        orderList = new ArrayList<>();
        adapter = new OrderAdapter();
        lvOrders.setAdapter(adapter);

        dbHelper.seedDummyOrdersIfEmpty();
        loadOrders();
    }

    private void loadOrders() {
        orderList.clear();
        Cursor cursor = dbHelper.getAllOrders();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndexOrThrow("order_number"));
                String student = cursor.getString(cursor.getColumnIndexOrThrow("student_name"));
                String vendor = cursor.getString(cursor.getColumnIndexOrThrow("vendor_name"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                orderList.add(new OrderItem(number, student, vendor, amount, status));
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private class OrderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderList.size();
        }

        @Override
        public Object getItem(int position) {
            return orderList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(Orders.this)
                        .inflate(R.layout.item_order, parent, false);
            }

            OrderItem item = orderList.get(position);

            TextView tvNumber = convertView.findViewById(R.id.tv_order_number);
            TextView tvStudent = convertView.findViewById(R.id.tv_student_name);
            TextView tvVendor = convertView.findViewById(R.id.tv_vendor_name);
            TextView tvAmount = convertView.findViewById(R.id.tv_order_amount);
            TextView tvStatus = convertView.findViewById(R.id.tv_order_status);

            tvNumber.setText("Order #" + item.orderNumber);
            tvStudent.setText("Student: " + item.studentName);
            tvVendor.setText("Vendor: " + item.vendorName);
            tvAmount.setText(String.format("Amount: R%.2f", item.amount));
            tvStatus.setText("Status: " + item.status);

            return convertView;
        }
    }
}