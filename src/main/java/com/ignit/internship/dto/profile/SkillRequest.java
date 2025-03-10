package com.ignit.internship.dto.profile;

public class SkillRequest {

    private String name;

    private String description;

    private int year;

    public SkillRequest(String name, String description, int year) {
        this.name = name;
        this.description = description;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    
}
