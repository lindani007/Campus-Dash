package com.example.campusdash;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {

    private EditText fullnameTxt, emailTxt, passwordTxt, phoneTxt;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fullnameTxt = findViewById(R.id.etFullName);
        emailTxt = findViewById(R.id.etRegEmail);
        passwordTxt = findViewById(R.id.etRegPassword);

        dbHelper = new DatabaseHelper(this);
    }

    public void createAccountClicked(View view) {
        String fullname = fullnameTxt != null ? fullnameTxt.getText().toString().trim() : "";
        String email = emailTxt != null ? emailTxt.getText().toString().trim() : "";
        String password = passwordTxt != null ? passwordTxt.getText().toString().trim() : "";

        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkUserExists(email)) {
            Toast.makeText(this, "An account with this email already exists!", Toast.LENGTH_SHORT).show();
            return;
        }


        String assignedRole = "Member";

        User newUser = new User(email, password, fullname, assignedRole, "");

        boolean inserted = dbHelper.insertUser(newUser);

        if (inserted) {
            Toast.makeText(this, "Account created as " + assignedRole + "!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void backClicked(View view) {
        finish();
    }
}