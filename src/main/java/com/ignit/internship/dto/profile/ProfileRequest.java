package com.ignit.internship.dto.profile;

public class ProfileRequest {

    private String fullName;

    private String passion;

    private String summary;

    public ProfileRequest(String fullName, String passion, String summary) {
        this.fullName = fullName;
        this.passion = passion;
        this.summary = summary;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassion() {
        return passion;
    }

    public String getSummary() {
        return summary;
    }
}
