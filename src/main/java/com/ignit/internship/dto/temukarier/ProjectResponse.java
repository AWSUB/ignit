package com.ignit.internship.dto.temukarier;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ignit.internship.enums.ProjectStatus;
import com.ignit.internship.model.temukarier.Project;

public class ProjectResponse {

    private Long id;

    private String name;

    private String description;

    private Long imageId;

    private ProjectStatus status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime deadline;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    private List<String> tags;

    private Long createdBy;

    public ProjectResponse(
        Long id, 
        String name, 
        String description, 
        Long imageId, 
        ProjectStatus status,    
        LocalDateTime deadline, 
        LocalDateTime createdAt, 
        LocalDateTime updatedAt, 
        List<String> tags,
        Long createdBy
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.status = status;
        this.deadline = deadline;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tags = tags;
        this.createdBy = createdBy;
    }

    public ProjectResponse(Project project) {
        this(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getImageId(),
            project.getStatus(),
            project.getDeadline(),
            project.getCreatedAt(),
            project.getUpdatedAt(),
            project.getTags().stream().map(t -> t.getName()).toList(),
            project.getProfile().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getImageId() {
        return imageId;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    
}
