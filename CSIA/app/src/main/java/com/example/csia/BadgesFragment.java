package com.example.csia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BadgesFragment extends Fragment {
    private RecyclerView badgesRecyclerView;
    private BadgeAdapter badgeAdapter;
    private List<Badge> badgeList;
    private TextView emptyStateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_badges, container, false);

        badgesRecyclerView = view.findViewById(R.id.badges_recycler_view);
        emptyStateText = view.findViewById(R.id.empty_state_text);

        badgesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        badgeList = new ArrayList<>();
        loadBadges();

        badgeAdapter = new BadgeAdapter(badgeList);
        badgesRecyclerView.setAdapter(badgeAdapter);

        return view;
    }

    private void loadBadges() {
        // Add sample badges - in a real app, these would come from Firebase
        badgeList.add(new Badge("First Habit", "Created your first habit", R.drawable.badge1, true));
        badgeList.add(new Badge("7-Day Streak", "Completed habits for 7 days straight", R.drawable.badge2, false));
        badgeList.add(new Badge("Early Bird", "Completed a habit before 8 AM", R.drawable.badge3, true));
        badgeList.add(new Badge("Weekend Warrior", "Completed a habit on the weekend", R.drawable.badge4, false));

        // Show empty state if no badges
        if (badgeList.isEmpty()) {
            emptyStateText.setVisibility(View.VISIBLE);
            badgesRecyclerView.setVisibility(View.GONE);
        } else {
            emptyStateText.setVisibility(View.GONE);
            badgesRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}