package com.example.campusdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText usernameTxt, passwordTxt;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameTxt = findViewById(R.id.etEmail);
        passwordTxt = findViewById(R.id.etPassword);
        dbHelper = new DatabaseHelper(this);
    }

    public void forgotPasswordClicked(View view) {
        Toast.makeText(this, "Forgot Password Clicked. This view is still coming", Toast.LENGTH_SHORT).show();
    }

    public void loginClicked(View view) {
        String email = usernameTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValid = dbHelper.validateUser(email, password);

        if (isValid) {
            User user = dbHelper.getUser(email);

            // Safe null handling
            String role = (user != null && user.getRole() != null) ? user.getRole() : "Member";
            String displayName = (user != null && user.getFullnames() != null) ? user.getFullnames() : email;

            Toast.makeText(this, "Welcome " + displayName + " (" + role + ")", Toast.LENGTH_SHORT).show();

            Intent intent;
            if (role.equalsIgnoreCase("Admin")) {
                intent = new Intent(MainActivity.this, AdminHome.class);
            }
            else if(role.equalsIgnoreCase("Vendor")) {
                intent = new Intent(MainActivity.this, VendorHomeActivity.class);
            }
            else {
                intent = new Intent(MainActivity.this, StudentHome.class);
            }

            intent.putExtra("USER_EMAIL", email);
            intent.putExtra("USER_ROLE", role);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerClicked(View view) {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}