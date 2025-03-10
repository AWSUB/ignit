package com.ignit.internship.dto.cakrawala;

import java.util.List;

public class InfoRequest {

    private String description;

    private String salaryRange;

    private String criteria;

    private List<String> skills;
    
    private List<String> relatedStudies;

    private List<String> careerOpportunities;

    private List<String> responsibilites;

    private List<String> questions;

    private Integer answer;

    private String tag;

    public InfoRequest(
        String description, 
        String salaryRange, 
        String criteria, 
        List<String> skills,
        List<String> relatedStudies, 
        List<String> careerOpportunities, 
        List<String> responsibilites,
        List<String> questions, 
        Integer answer, 
        String tag
    ) {
        this.description = description;
        this.salaryRange = salaryRange;
        this.criteria = criteria;
        this.skills = skills;
        this.relatedStudies = relatedStudies;
        this.careerOpportunities = careerOpportunities;
        this.responsibilites = responsibilites;
        this.questions = questions;
        this.answer = answer;
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public String getCriteria() {
        return criteria;
    }

    public List<String> getSkills() {
        return skills;
    }

    public List<String> getRelatedStudies() {
        return relatedStudies;
    }

    public List<String> getCareerOpportunities() {
        return careerOpportunities;
    }

    public List<String> getResponsibilites() {
        return responsibilites;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public Integer getAnswer() {
        return answer;
    }

    public String getTag() {
        return tag;
    }
}
