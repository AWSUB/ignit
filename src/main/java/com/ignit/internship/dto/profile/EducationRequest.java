package com.ignit.internship.dto.profile;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EducationRequest {

    private String degree;

    private String school;

    private String fieldOfStudy;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    public EducationRequest(String degree, String school, String fieldOfStudy, LocalDate startDate, LocalDate endDate) {
        this.degree = degree;
        this.school = school;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDegree() {
        return degree;
    }

    public String getSchool() {
        return school;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    
}
