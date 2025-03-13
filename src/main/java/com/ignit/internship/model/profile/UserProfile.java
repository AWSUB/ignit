package com.ignit.internship.model.profile;

import java.util.ArrayList;
import java.util.List;

import com.ignit.internship.model.auth.User;
import com.ignit.internship.model.belajaryuk.StudyPackage;
import com.ignit.internship.model.temukarier.Project;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile {

    @Id
    private Long id;

    private Long imageId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String fullName;

    private String passion;

    @Column(columnDefinition = "text")
    private String summary;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private User user;

    @ManyToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Project> projects;

    @ManyToMany(mappedBy = "profiles", cascade = CascadeType.ALL)
    private List<StudyPackage> studyPackages;

    @SuppressWarnings("unused")
    private UserProfile() {}

    public UserProfile(User user) {
        this.user = user;
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.educations = new ArrayList<Education>();
        this.skills = new ArrayList<Skill>();
        this.projects = new ArrayList<Project>();
        this.studyPackages = new ArrayList<StudyPackage>();
    }

    public Long getId() {
        return id;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassion() {
        return passion;
    }

    public void setPassion(String passion) {
        this.passion = passion;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void addEducation(Education education) {
        this.educations.add(education);
    }

    public void removeEducation(Long id) {
        this.educations.removeIf((education) -> {
            return education.getId() == id;
        });
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public void removeSkill(Long id) {
        this.skills.removeIf((skill) -> {
            return skill.getId() == id;
        });
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public List<StudyPackage> getStudyPackages() {
        return studyPackages;
    }

    public void addStudyPackage(StudyPackage studyPackage) {
        this.studyPackages.add(studyPackage);
    }
}
