package com.example.campusdash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VendorDeliveryGuysActivity extends AppCompatActivity {

    private EditText etGuyName, etGuyEmail;
    private Button btnAddGuy;
    private TextView btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_delivery_guys);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        etGuyName = findViewById(R.id.etGuyName);
        etGuyEmail = findViewById(R.id.etGuyEmail);
        btnAddGuy = findViewById(R.id.btnAddGuy);

        btnAddGuy.setOnClickListener(v -> {
            String name = etGuyName.getText().toString().trim();
            String email = etGuyEmail.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Enter name and email", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertDeliveryGuy(name, email);
            if (inserted) {
                Toast.makeText(this, "Delivery driver added!", Toast.LENGTH_SHORT).show();
                etGuyName.setText("");
                etGuyEmail.setText("");
            } else {
                Toast.makeText(this, "Failed to add driver", Toast.LENGTH_SHORT).show();
            }
        });
    }
}