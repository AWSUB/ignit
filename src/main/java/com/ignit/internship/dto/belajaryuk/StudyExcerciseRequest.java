package com.ignit.internship.dto.belajaryuk;

public class StudyExcerciseRequest {

    private String title;

    private String content;

    public StudyExcerciseRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
