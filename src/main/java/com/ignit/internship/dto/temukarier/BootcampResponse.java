package com.ignit.internship.dto.temukarier;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ignit.internship.model.temukarier.Bootcamp;

public class BootcampResponse {

    private Long id;

    private String name;

    private String description;

    private Long imageId;

    private String url;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;

    private List<String> tags;

    public BootcampResponse(
        Long id, 
        String name, 
        String description, 
        Long imageId, 
        String url, 
        LocalDateTime createdAt,
        LocalDateTime updatedAt, 
        List<String> tags
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageId = imageId;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tags = tags;
    }

    public BootcampResponse(Bootcamp bootcamp) {
        this(
            bootcamp.getId(),
            bootcamp.getName(),
            bootcamp.getDescription(),
            bootcamp.getImageId(),
            bootcamp.getUrl(),
            bootcamp.getCreatedAt(),
            bootcamp.getUpdatedAt(),
            bootcamp.getTags().stream().map(t -> t.getName()).toList()
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

    public String getUrl() {
        return url;
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
}
