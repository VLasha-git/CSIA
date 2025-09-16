package com.example.csia;
import java.util.List;

public class Habit {
    private String id;
    private String name;
    private String description;
    private String frequency;
    private List<String> completionDates;

    public Habit() {
        // Default constructor required for Firebase
    }

    public Habit(String id, String name, String description, String frequency, List<String> completionDates) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.completionDates = completionDates;
    }


    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public List<String> getCompletionDates() { return completionDates; }
    public void setCompletionDates(List<String> completionDates) { this.completionDates = completionDates; }
}
