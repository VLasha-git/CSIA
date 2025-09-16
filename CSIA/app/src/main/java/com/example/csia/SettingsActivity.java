package com.example.csia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    private SwitchCompat notificationsSwitch;
    private Button changePasswordButton, deleteAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationsSwitch = findViewById(R.id.notifications_switch);
        changePasswordButton = findViewById(R.id.change_password_button);
        deleteAccountButton = findViewById(R.id.delete_account_button);

        // Load saved settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notificationsEnabled = preferences.getBoolean("notifications", true);
        notificationsSwitch.setChecked(notificationsEnabled);

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("notifications", isChecked);
            editor.apply();
        });

        changePasswordButton.setOnClickListener(v -> changePassword());
        deleteAccountButton.setOnClickListener(v -> deleteAccount());
    }

    private void changePassword() {
        // Implement password change logic
        Toast.makeText(this, "Password change feature coming soon", Toast.LENGTH_SHORT).show();
    }

    private void deleteAccount() {
        // Implement account deletion logic
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete your account? This action cannot be undone.");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Delete user from Firebase
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.delete().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SettingsActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SettingsActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}
