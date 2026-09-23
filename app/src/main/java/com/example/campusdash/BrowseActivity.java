package com.example.campusdash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewStores;
    private StoreAdapter storeAdapter;
    private List<Store> storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        dbHelper = new DatabaseHelper(this);
        recyclerViewStores = findViewById(R.id.recyclerViewStores);
        recyclerViewStores.setLayoutManager(new LinearLayoutManager(this));

        storeList = loadStoresFromDatabase();

        storeAdapter = new StoreAdapter(storeList, store -> {
            Intent intent = new Intent(BrowseActivity.this, VendorMenuActivity.class);
            intent.putExtra("STORE_ID", store.getStoreid());
            intent.putExtra("STORE_NAME", store.getStorename());
            startActivity(intent);
        });

        recyclerViewStores.setAdapter(storeAdapter);
    }

    private List<Store> loadStoresFromDatabase() {
        List<Store> list = new ArrayList<>();
        Cursor cursor = dbHelper.getAllVendorsWithStores();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int storeId = cursor.getInt(cursor.getColumnIndexOrThrow("storeid"));
                String storeName = cursor.getString(cursor.getColumnIndexOrThrow("storeName"));
                String vendorName = cursor.getString(cursor.getColumnIndexOrThrow("vendorName"));
                int vendorId = cursor.getInt(cursor.getColumnIndexOrThrow("vendorid"));

                if (storeName != null && !storeName.isEmpty()) {
                    list.add(new Store(storeId, storeName, vendorName, vendorId, "", "5.0"));
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }
}