package com.example.csia;

public class Badge {
    private String name;
    private String description;
    private int imageResource;
    private boolean earned;

    public Badge(String name, String description, int imageResource, boolean earned) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.earned = earned;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getImageResource() { return imageResource; }
    public void setImageResource(int imageResource) { this.imageResource = imageResource; }

    public boolean isEarned() { return earned; }
    public void setEarned(boolean earned) { this.earned = earned; }
}
