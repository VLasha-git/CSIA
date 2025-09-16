package com.example.csia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {
    private List<Habit> habitList;

    public static class HabitViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, descriptionTextView, frequencyTextView;
        public CheckBox completedCheckBox;

        public HabitViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.habit_name);
            descriptionTextView = itemView.findViewById(R.id.habit_description);
            frequencyTextView = itemView.findViewById(R.id.habit_frequency);
            completedCheckBox = itemView.findViewById(R.id.completed_checkbox);
        }
    }

    public HabitAdapter(List<Habit> habitList) {
        this.habitList = habitList;
    }

    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_item, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);
        holder.nameTextView.setText(habit.getName());
        holder.descriptionTextView.setText(habit.getDescription());
        holder.frequencyTextView.setText(habit.getFrequency());

        // Check if habit was completed today
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        boolean completedToday = habit.getCompletionDates() != null && habit.getCompletionDates().contains(today);
        holder.completedCheckBox.setChecked(completedToday);

        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !completedToday) {
                // Add today's date to completion dates
                if (habit.getCompletionDates() == null) {
                    habit.setCompletionDates(new ArrayList<>());
                }
                habit.getCompletionDates().add(today);
                updateHabit(habit);
            } else if (!isChecked && completedToday) {
                // Remove today's date from completion dates
                if (habit.getCompletionDates() != null) {
                    habit.getCompletionDates().remove(today);
                    updateHabit(habit);
                }
            }
        });
    }

    private void updateHabit(Habit habit) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("habits")
                .child(userId)
                .child(habit.getId())
                .setValue(habit);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }
}