package com.ignit.internship.dto.temukarier;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ignit.internship.enums.ProjectStatus;

public class ProjectRequest {

    private String name;

    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime deadline;

    private ProjectStatus status;

    private List<String> tags;

    public ProjectRequest(
        String name, 
        String description, 
        LocalDateTime deadline, 
        ProjectStatus status, 
        List<String> tags
    ) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public List<String> getTags() {
        return tags;
    }
}
