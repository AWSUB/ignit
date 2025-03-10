package com.ignit.internship.dto.profile;

import java.util.List;

import com.ignit.internship.dto.temukarier.ProjectResponse;
import com.ignit.internship.model.profile.UserProfile;

public class ProfileResponse {

    private Long id;

    private String username;

    private String email;

    private String fullName;

    private String passion;

    private String summary;

    private List<EducationResponse> educations;
    
    private List<SkillResponse> skills;

    private List<ProjectResponse> projects;

    public ProfileResponse(
        Long id, 
        String username, 
        String email, 
        String fullName, 
        String passion, 
        String summary,
        List<EducationResponse> educations, 
        List<SkillResponse> skills, 
        List<ProjectResponse> projects
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.passion = passion;
        this.summary = summary;
        this.educations = educations;
        this.skills = skills;
        this.projects = projects;
    }

    public ProfileResponse(UserProfile profile) {
        this(
            profile.getId(), 
            profile.getUsername(), 
            profile.getEmail(), 
            profile.getFullName(), 
            profile.getPassion(), 
            profile.getSummary(), 
            profile.getEducations().stream().map(ed -> new EducationResponse(ed)).toList(),
            profile.getSkills().stream().map(sk -> new SkillResponse(sk)).toList(),
            profile.getProjects().stream().map(p -> new ProjectResponse(p)).toList()
        );
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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

    public List<EducationResponse> getEducations() {
        return educations;
    }

    public List<SkillResponse> getSkills() {
        return skills;
    }

    public List<ProjectResponse> getProjects() {
        return projects;
    }

    
}
