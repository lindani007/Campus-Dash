package com.example.campusdash;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VendorManagementActivity extends AppCompatActivity {

    private ListView lvVendors;
    private Button btnAddVendor;
    private ImageButton btnBack;
    private DatabaseHelper dbHelper;
    private VendorAdapter adapter;
    private List<VendorItem> vendorList;

    public static class VendorItem {
        int vendorId;
        String vendorName;
        String vendorEmail;
        String storeName;

        public VendorItem(int vendorId, String vendorName, String vendorEmail, String storeName) {
            this.vendorId = vendorId;
            this.vendorName = vendorName;
            this.vendorEmail = vendorEmail;
            this.storeName = storeName;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_management);

        dbHelper = new DatabaseHelper(this);
        lvVendors = findViewById(R.id.lv_vendors);
        btnAddVendor = findViewById(R.id.btn_add_vendor);
        btnBack = findViewById(R.id.btn_back);

        // Back button action
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        vendorList = new ArrayList<>();
        adapter = new VendorAdapter();
        lvVendors.setAdapter(adapter);

        btnAddVendor.setOnClickListener(v -> showAddVendorDialog());

        loadVendors();
    }

    private void loadVendors() {
        vendorList.clear();
        Cursor cursor = dbHelper.getAllVendorsWithStores();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("vendorid"));
                String vName = cursor.getString(cursor.getColumnIndexOrThrow("vendorName"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("vendorEmail"));
                String sName = cursor.getString(cursor.getColumnIndexOrThrow("storeName"));

                vendorList.add(new VendorItem(id, vName, email, sName != null ? sName : "No Store"));
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private void showAddVendorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_vendor, null);

        EditText etVendorName = view.findViewById(R.id.et_vendor_name);
        EditText etVendorEmail = view.findViewById(R.id.et_vendor_email);
        EditText etStoreName = view.findViewById(R.id.et_store_name);
        EditText etVendorPhone = view.findViewById(R.id.et_vendor_phone);

        builder.setView(view)
                .setPositiveButton("Add Vendor", (dialog, which) -> {
                    String vName = etVendorName.getText().toString().trim();
                    String email = etVendorEmail.getText().toString().trim();
                    String sName = etStoreName.getText().toString().trim();
                    String phone = etVendorPhone.getText().toString().trim();

                    if (vName.isEmpty() || email.isEmpty() || sName.isEmpty()) {
                        Toast.makeText(this, "Please fill in Name, Email and Store Name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (dbHelper.checkUserExists(email)) {
                        Toast.makeText(this, "A user with this email already exists!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    boolean success = dbHelper.addVendorAndStore(vName, email, sName, phone);
                    if (success) {
                        String defaultPassword = sName.replaceAll("\\s+", "").toLowerCase() + "@123";
                        Toast.makeText(this, "Vendor added! Default Password: " + defaultPassword, Toast.LENGTH_LONG).show();
                        loadVendors();
                    } else {
                        Toast.makeText(this, "Failed to add vendor.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private class VendorAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return vendorList.size();
        }

        @Override
        public Object getItem(int position) {
            return vendorList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return vendorList.get(position).vendorId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(VendorManagementActivity.this)
                        .inflate(R.layout.item_vendor, parent, false);
            }

            VendorItem item = vendorList.get(position);

            TextView tvStoreName = convertView.findViewById(R.id.tv_store_name);
            TextView tvVendorName = convertView.findViewById(R.id.tv_vendor_name);
            TextView tvVendorEmail = convertView.findViewById(R.id.tv_vendor_email);
            Button btnDelete = convertView.findViewById(R.id.btn_delete_vendor);

            tvStoreName.setText(item.storeName);
            tvVendorName.setText("Owner: " + item.vendorName);
            tvVendorEmail.setText(item.vendorEmail);

            btnDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(VendorManagementActivity.this)
                        .setTitle("Delete Vendor")
                        .setMessage("Are you sure you want to delete " + item.vendorName + " and their store?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            boolean deleted = dbHelper.deleteVendor(item.vendorId);
                            if (deleted) {
                                Toast.makeText(VendorManagementActivity.this, "Vendor deleted", Toast.LENGTH_SHORT).show();
                                loadVendors();
                            } else {
                                Toast.makeText(VendorManagementActivity.this, "Failed to delete vendor", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            });

            return convertView;
        }
    }
}