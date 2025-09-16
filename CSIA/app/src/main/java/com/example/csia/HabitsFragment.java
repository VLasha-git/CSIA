package com.example.csia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HabitsFragment extends Fragment {
    private RecyclerView habitsRecyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habitList;
    private FirebaseDatabase database;
    private DatabaseReference habitsRef;
    private String userId;
    private TextView emptyStateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habits, container, false);

        // Initialize Firebase
        database = FirebaseDatabase.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        habitsRef = database.getReference("habits").child(userId);

        // Setup empty state text
        emptyStateText = view.findViewById(R.id.empty_state_text);

        // Setup recycler view
        habitsRecyclerView = view.findViewById(R.id.habits_recycler_view);
        habitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        habitList = new ArrayList<>();
        habitAdapter = new HabitAdapter(habitList);
        habitsRecyclerView.setAdapter(habitAdapter);

        // Load habits from Firebase
        loadHabits();

        return view;
    }

    public void loadHabits() {
        habitsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                habitList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Habit habit = snapshot.getValue(Habit.class);
                    if (habit != null) {
                        habitList.add(habit);
                    }
                }
                habitAdapter.notifyDataSetChanged();

                // Show empty state if no habits
                if (habitList.isEmpty()) {
                    emptyStateText.setVisibility(View.VISIBLE);
                    habitsRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyStateText.setVisibility(View.GONE);
                    habitsRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load habits", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Public method to refresh habits (called from MainActivity)
    public void refreshHabits() {
        loadHabits();
    }

    // Refresh when fragment becomes visible
    @Override
    public void onResume() {
        super.onResume();
        loadHabits();
    }
}