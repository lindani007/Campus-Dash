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

public class PaymentActivity extends AppCompatActivity {

    private ListView lvPayments;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private PaymentAdapter adapter;
    private List<PaymentItem> paymentList;

    public static class PaymentItem {
        String paymentNumber;
        String studentName;
        String vendorName;
        double amount;
        String method;
        String status;

        public PaymentItem(String paymentNumber, String studentName, String vendorName, double amount, String method, String status) {
            this.paymentNumber = paymentNumber;
            this.studentName = studentName;
            this.vendorName = vendorName;
            this.amount = amount;
            this.method = method;
            this.status = status;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        lvPayments = findViewById(R.id.lv_payments);
        btnBack = findViewById(R.id.btn_back);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        paymentList = new ArrayList<>();
        adapter = new PaymentAdapter();
        lvPayments.setAdapter(adapter);

        dbHelper.seedDummyPaymentsIfEmpty();
        loadPayments();
    }

    private void loadPayments() {
        paymentList.clear();
        Cursor cursor = dbHelper.getAllPayments();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndexOrThrow("payment_number"));
                String student = cursor.getString(cursor.getColumnIndexOrThrow("student_name"));
                String vendor = cursor.getString(cursor.getColumnIndexOrThrow("vendor_name"));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
                String method = cursor.getString(cursor.getColumnIndexOrThrow("method"));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));

                paymentList.add(new PaymentItem(number, student, vendor, amount, method, status));
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private class PaymentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return paymentList.size();
        }

        @Override
        public Object getItem(int position) {
            return paymentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(PaymentActivity.this)
                        .inflate(R.layout.item_payment, parent, false);
            }

            PaymentItem item = paymentList.get(position);

            TextView tvNumber = convertView.findViewById(R.id.tv_payment_number);
            TextView tvStudent = convertView.findViewById(R.id.tv_student_name);
            TextView tvVendor = convertView.findViewById(R.id.tv_vendor_name);
            TextView tvAmount = convertView.findViewById(R.id.tv_payment_amount);
            TextView tvMethod = convertView.findViewById(R.id.tv_payment_method);
            TextView tvStatus = convertView.findViewById(R.id.tv_payment_status);

            tvNumber.setText("Payment #" + item.paymentNumber);
            tvStudent.setText("Student: " + item.studentName);
            tvVendor.setText("Vendor: " + item.vendorName);
            tvAmount.setText(String.format("Amount: R%.2f", item.amount));
            tvMethod.setText("Method: " + item.method);
            tvStatus.setText("Status: " + item.status);

            return convertView;
        }
    }
}