package com.example.csia;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {
    private List<Badge> badgeList;

    public static class BadgeViewHolder extends RecyclerView.ViewHolder {
        public ImageView badgeImageView;
        public TextView badgeNameTextView, badgeDescriptionTextView;

        public BadgeViewHolder(View itemView) {
            super(itemView);
            badgeImageView = itemView.findViewById(R.id.badge_image);
            badgeNameTextView = itemView.findViewById(R.id.badge_name);
            badgeDescriptionTextView = itemView.findViewById(R.id.badge_description);
        }
    }

    public BadgeAdapter(List<Badge> badgeList) {
        this.badgeList = badgeList;
    }

    @Override
    public BadgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_item, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BadgeViewHolder holder, int position) {
        Badge badge = badgeList.get(position);
        holder.badgeNameTextView.setText(badge.getName());
        holder.badgeDescriptionTextView.setText(badge.getDescription());
        holder.badgeImageView.setImageResource(badge.getImageResource());

        if (!badge.isEarned()) {
            holder.badgeImageView.setColorFilter(Color.argb(150, 0, 0, 0)); // Darken unearned badges
        }
    }

    @Override
    public int getItemCount() {
        return badgeList.size();
    }
}
