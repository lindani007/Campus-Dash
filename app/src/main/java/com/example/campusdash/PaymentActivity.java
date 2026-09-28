package com.example.campusdash;

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
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {

    private ListView lvPayments;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private PaymentAdapter adapter;
    private List<Payment> paymentList;

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
        // Use getAllPaymentsList() which returns List<Payment> from DatabaseHelper
        List<Payment> list = dbHelper.getAllPaymentsList();
        if (list != null) {
            paymentList.addAll(list);
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

            Payment item = paymentList.get(position);

            TextView tvNumber = convertView.findViewById(R.id.tv_payment_number);
            TextView tvStudent = convertView.findViewById(R.id.tv_student_name);
            TextView tvVendor = convertView.findViewById(R.id.tv_vendor_name);
            TextView tvAmount = convertView.findViewById(R.id.tv_payment_amount);
            TextView tvMethod = convertView.findViewById(R.id.tv_payment_method);
            TextView tvStatus = convertView.findViewById(R.id.tv_payment_status);

            tvNumber.setText("Payment #" + item.getPaymentNumber());
            tvStudent.setText("Student: " + item.getStudentName());
            tvVendor.setText("Vendor: " + item.getVendorName());
            tvAmount.setText(String.format(Locale.getDefault(), "Amount: R%.2f", item.getAmount()));
            tvMethod.setText("Method: " + item.getMethod());
            tvStatus.setText("Status: " + item.getStatus());

            return convertView;
        }
    }
}
