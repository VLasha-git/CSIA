package com.example.csia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class User {
    private String email;
    private String joinDate;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String email) {
        this.email = email;
        this.joinDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
}
