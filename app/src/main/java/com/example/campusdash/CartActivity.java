package com.example.campusdash;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.campusdash.R;

public class CartActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<CartItem> cartList;

    private TextView tvSubtotal, tvDeliveryFee, tvTotal, btnBack;
    private RadioGroup rgService, rgPayment;
    private Button btnCheckout;

    private double subtotal = 0.0;
    private double deliveryFee = 10.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbHelper = new DatabaseHelper(this);

        // Bind Back Button & Set Click Listener
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        tvTotal = findViewById(R.id.tvTotal);
        rgService = findViewById(R.id.rgService);
        rgPayment = findViewById(R.id.rgPayment);
        btnCheckout = findViewById(R.id.btnCheckout);

        rgService.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCollect) {
                deliveryFee = 0.0;
            } else {
                deliveryFee = 10.0;
            }
            updateTotals();
        });

        btnCheckout.setOnClickListener(v -> placeOrderAndClearCart());

        loadCartData();
    }

    private void loadCartData() {
        cartList = new ArrayList<>();
        subtotal = 0.0;

        Cursor cursor = dbHelper.getCartItems();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int cartId = cursor.getInt(cursor.getColumnIndexOrThrow("cartid"));
                int mealId = cursor.getInt(cursor.getColumnIndexOrThrow("mealid"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("mealname"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("mealprice"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("meaimage"));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                cartList.add(new CartItem(cartId, mealId, name, price, image, qty));
                subtotal += (price * qty);
            } while (cursor.moveToNext());
            cursor.close();
        }

        cartAdapter = new CartAdapter(cartList, (item, newQty) -> {
            dbHelper.updateCartQuantity(item.getCartId(), newQty);
            loadCartData(); // Reload UI when items update
        });

        recyclerViewCart.setAdapter(cartAdapter);
        updateTotals();
    }

    private void updateTotals() {
        tvSubtotal.setText("R" + String.format("%.2f", subtotal));
        tvDeliveryFee.setText("R" + String.format("%.2f", deliveryFee));
        tvTotal.setText("R" + String.format("%.2f", subtotal + deliveryFee));
    }

    private void placeOrderAndClearCart() {
        if (cartList == null || cartList.isEmpty()) {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        double grandTotal = subtotal + deliveryFee;
        String orderNumber = "ORD-" + System.currentTimeMillis() % 10000;

        // Save order inside database
        boolean orderSaved = dbHelper.insertOrder(orderNumber, "Student User", "Campus Vendor", grandTotal, "Pending");

        if (orderSaved) {
            // Clear cart from database
            dbHelper.clearCart();
            Toast.makeText(this, "Order placed successfully! Cart cleared.", Toast.LENGTH_LONG).show();
            finish(); // Return to previous activity
        } else {
            Toast.makeText(this, "Failed to place order. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}