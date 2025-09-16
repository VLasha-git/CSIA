package com.example.csia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateHabitActivity extends AppCompatActivity {
    private static final String TAG = "CreateHabitActivity";

    private EditText habitNameEditText, habitDescriptionEditText;
    private Spinner frequencySpinner;
    private Button createHabitButton;
    private DatabaseReference habitsRef;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);

        Log.d(TAG, "Activity created");

        // Initialize Firebase
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() == null) {
                Log.e(TAG, "No user logged in");
                Toast.makeText(this, "Please log in first", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            userId = auth.getCurrentUser().getUid();
            habitsRef = FirebaseDatabase.getInstance().getReference("habits").child(userId);
            Log.d(TAG, "Firebase initialized successfully. User ID: " + userId);
        } catch (Exception e) {
            Log.e(TAG, "Firebase initialization failed: " + e.getMessage());
            Toast.makeText(this, "Database error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Initialize views
        habitNameEditText = findViewById(R.id.habit_name);
        habitDescriptionEditText = findViewById(R.id.habit_description);
        frequencySpinner = findViewById(R.id.frequency_spinner);
        createHabitButton = findViewById(R.id.create_habit_button);

        // Setup frequency spinner
        setupFrequencySpinner();

        // Set click listener - SIMPLE VERSION FOR RETURNING TO MAIN ACTIVITY
        createHabitButton.setOnClickListener(v -> {
            Log.d(TAG, "Create habit button clicked");

            // Get input values
            String name = habitNameEditText.getText().toString().trim();

            // Validate input
            if (name.isEmpty()) {
                habitNameEditText.setError("Habit name is required");
                habitNameEditText.requestFocus();
                return;
            }



            // Return to MainActivity
            Intent intent = new Intent(CreateHabitActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Log.d(TAG, "Activity setup complete");
    }

    private void setupFrequencySpinner() {
        List<String> frequencyOptions = new ArrayList<>();
        frequencyOptions.add("Daily");
        frequencyOptions.add("Weekly");
        frequencyOptions.add("Monthly");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                frequencyOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(adapter);
    }


    }
