package com.ignit.internship.dto.profile;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ignit.internship.model.profile.Education;

public class EducationResponse {

    private Long id;

    private String degree;

    private String school;

    private String fieldOfStudy;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    public EducationResponse(
        Long id,
        String degree, 
        String school, 
        String fieldOfStudy, 
        LocalDate startDate,
        LocalDate endDate
    ) {
        this.id = id;
        this.degree = degree;
        this.school = school;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EducationResponse(Education education) {
        this(
            education.getId(),
            education.getDegree(),
            education.getSchool(),
            education.getFieldOfStudy(),
            education.getStartDate(),
            education.getEndDate()
        );
    }

    public Long getId() {
        return id;
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
