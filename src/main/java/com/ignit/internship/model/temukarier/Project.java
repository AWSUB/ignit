package com.ignit.internship.model.temukarier;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ignit.internship.enums.ProjectStatus;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.model.utils.Tag;

import static jakarta.persistence.CascadeType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private Long imageId;

    private ProjectStatus status;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime deadline;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Tag> tags;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private UserProfile profile;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<UserProfile> members;

    @SuppressWarnings("unused")
    private Project() {}

    public Project(
        String name, 
        String description,
        Long imageId,
        ProjectStatus status, 
        LocalDateTime deadline, 
        List<Tag> tags,
        UserProfile profile
    ) {
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.status = status;
        this.deadline = deadline;
        this.tags = tags;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setDescription(String decription) {
        this.description = decription;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public List<UserProfile> getMembers() {
        return members;
    }

    public void addMembers(UserProfile profile) {
        this.members.add(profile);
    }
}
