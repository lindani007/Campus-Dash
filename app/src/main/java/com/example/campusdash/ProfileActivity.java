package com.example.campusdash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView tvProfileName, tvProfileEmail, btnBack;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DatabaseHelper(this);

        // Bind Back Button & Set Click Listener
        btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish()); // Closes profile and returns to previous screen
        }

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        btnLogout = findViewById(R.id.btnLogout);

        loadUserProfile();

        btnLogout.setOnClickListener(v -> performLogout());
    }

    private void loadUserProfile() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String loggedInEmail = prefs.getString("USER_EMAIL", "");

        if (!loggedInEmail.isEmpty()) {
            tvProfileEmail.setText(loggedInEmail);

            // Fetch user name from SQLite database using the email address
            Cursor cursor = dbHelper.getUserByEmail(loggedInEmail);
            if (cursor != null && cursor.moveToFirst()) {
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                if (fullName != null && !fullName.isEmpty()) {
                    tvProfileName.setText(fullName);
                } else {
                    tvProfileName.setText("Student User");
                }
                cursor.close();
            } else {
                tvProfileName.setText("Student User");
            }
        } else {
            tvProfileName.setText("Lindani Ndwandwe");
            tvProfileEmail.setText("25008616@dut4life.ac.za");
        }
    }

    private void performLogout() {
        // Clear saved session tokens/preferences
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity and clear backstack so user cannot click 'Back' to return
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}