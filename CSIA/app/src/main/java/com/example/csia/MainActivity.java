package com.example.csia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    // Define your own ID constants
    private static final int NAVIGATION_HABITS = 1001;
    private static final int NAVIGATION_QUOTES = 1002;
    private static final int NAVIGATION_BADGES = 1003;
    private static final int ACTION_SETTINGS = 2001;
    private static final int ACTION_LOGOUT = 2002;

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FloatingActionButton addHabitButton;

    // Modern way to handle activity results
    private final ActivityResultLauncher<Intent> createHabitLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Toast.makeText(MainActivity.this, "Habit created successfully!", Toast.LENGTH_SHORT).show();
                    refreshHabitsFragment();
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    Toast.makeText(MainActivity.this, "Habit creation cancelled", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views safely
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        addHabitButton = findViewById(R.id.add_habit_button);
        fragmentManager = getSupportFragmentManager();

        // Check if FAB exists before setting click listener
        if (addHabitButton != null) {
            addHabitButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, CreateHabitActivity.class);
                createHabitLauncher.launch(intent);
            });
        }

        setupBottomNavigation();

        // Load default fragment
        loadFragment(new HabitsFragment());
    }

    private void setupBottomNavigation() {
        if (bottomNavigationView == null) return;

        // Clear any existing menu items
        bottomNavigationView.getMenu().clear();

        // Add items programmatically
        bottomNavigationView.getMenu().add(Menu.NONE, NAVIGATION_HABITS, Menu.NONE, "Habits")
                .setIcon(R.drawable.ic_habits);

        bottomNavigationView.getMenu().add(Menu.NONE, NAVIGATION_QUOTES, Menu.NONE, "Quotes")
                .setIcon(R.drawable.ic_quotes);

        bottomNavigationView.getMenu().add(Menu.NONE, NAVIGATION_BADGES, Menu.NONE, "Badges")
                .setIcon(R.drawable.ic_badges);

        // Set up navigation listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == NAVIGATION_HABITS) {
                loadFragment(new HabitsFragment());
                return true;
            } else if (itemId == NAVIGATION_QUOTES) {
                loadFragment(new QuotesFragment());
                return true;
            } else if (itemId == NAVIGATION_BADGES) {
                loadFragment(new BadgesFragment());
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // Create options menu programmatically
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();

        // Add settings item
        MenuItem settingsItem = menu.add(Menu.NONE, ACTION_SETTINGS, Menu.NONE, "Settings");
        settingsItem.setIcon(R.drawable.ic_settings);
        settingsItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // Add logout item
        MenuItem logoutItem = menu.add(Menu.NONE, ACTION_LOGOUT, Menu.NONE, "Logout");
        logoutItem.setIcon(R.drawable.ic_logout);
        logoutItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == ACTION_SETTINGS) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (itemId == ACTION_LOGOUT) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshHabitsFragment() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (currentFragment instanceof HabitsFragment) {
            ((HabitsFragment) currentFragment).refreshHabits();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHabitsFragment();
    }
}