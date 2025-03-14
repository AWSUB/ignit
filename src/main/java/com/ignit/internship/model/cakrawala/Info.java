package com.ignit.internship.model.cakrawala;

import java.util.List;

import com.ignit.internship.model.utils.Tag;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;

@Entity
public class Info {

    @Id
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private String salaryRange;

    @Column(columnDefinition = "text")
    private String criteria;

    @ElementCollection
    private List<String> skills;
    
    @ElementCollection
    private List<String> relatedStudies;

    @ElementCollection
    private List<String> careerOpportunities;

    @ElementCollection
    private List<String> responsibilites;

    private String question;

    @ElementCollection
    private List<String> options;

    private Integer answer;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Tag tag;

    @ElementCollection
    private List<Long> imageIds;

    @Version
    private Long version;

    @SuppressWarnings("unused")
    private Info() {}

    public Info(
        String description, 
        String salaryRange,
        String criteria, 
        List<String> skills,
        List<String> relatedStudies, 
        List<String> careerOpportunities, 
        List<String> responsibilites,
        String question,
        List<String> options,
        Integer answer,
        Tag tag,
        List<Long> imageIds
    ) {
        this.description = description;
        this.salaryRange = salaryRange;
        this.criteria = criteria;
        this.skills = skills;
        this.relatedStudies = relatedStudies;
        this.careerOpportunities = careerOpportunities;
        this.responsibilites = responsibilites;
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.tag = tag;
        this.imageIds = imageIds;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getRelatedStudies() {
        return relatedStudies;
    }

    public void setRelatedStudies(List<String> relatedStudies) {
        this.relatedStudies = relatedStudies;
    }

    public List<String> getCareerOpportunities() {
        return careerOpportunities;
    }

    public void setCareerOpportunities(List<String> careerOpportunities) {
        this.careerOpportunities = careerOpportunities;
    }

    public List<String> getResponsibilites() {
        return responsibilites;
    }

    public void setResponsibilites(List<String> responsibilites) {
        this.responsibilites = responsibilites;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> questions) {
        this.options = questions;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Tag getTag() {
        return tag;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }
}
