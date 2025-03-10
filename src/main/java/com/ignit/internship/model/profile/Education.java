package com.ignit.internship.model.profile;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;

    private String school;

    private String fieldOfStudy;

    @Temporal(value = TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(value = TemporalType.DATE)
    private LocalDate endDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserProfile profile;

    @SuppressWarnings("unused")
    private Education() {}

    public Education(
        String degree, 
        String school, 
        String fieldOfStudy, 
        LocalDate startDate, 
        LocalDate endDate,
        UserProfile profile
    ) {
        this.degree = degree;
        this.school = school;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
