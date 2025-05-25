package com.example.studentmanagementsystem;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.example.studentmanagementsystem.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    // Declaration of binding object to access UI components and DataBaseHelper for database interactions
    ActivitySignupBinding binding;
    DataBaseHelper_1 databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initializing the binding object to bind the XML layout with the activity
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // Setting the content view for the activity

        // Initializing the database helper to handle database operations
        databaseHelper = new DataBaseHelper_1(this);

        // Setting click listener for the signup button
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fetching user inputs from UI components
                String email = binding.signupEmail.getText().toString().trim();
                String password = binding.signupPassword.getText().toString().trim();
                String confirmPassword = binding.signupConfirm.getText().toString().trim();

                // Validation checks for empty fields
                if(email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(SignupActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                // Validation check for valid email format
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignupActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Check if passwords match
                    if(password.equals(confirmPassword)) {
                        // Check if the email already exists in the database
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if(!checkUserEmail) {
                            // Insert user data into the database
                            Boolean insert = databaseHelper.insertData(email, password);

                            if(insert) {
                                // Notify user of successful signup and redirect to the main activity
                                Toast.makeText(SignupActivity.this, "Signup Successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            } else {
                                // Notify user if signup failed
                                Toast.makeText(SignupActivity.this, "Signup Failed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Notify user if email is already registered
                            Toast.makeText(SignupActivity.this, "User already exists! Please login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Notify user if passwords do not match
                        Toast.makeText(SignupActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Setting click listener for redirecting to the login screen
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirecting to the MainActivity (login page)
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
