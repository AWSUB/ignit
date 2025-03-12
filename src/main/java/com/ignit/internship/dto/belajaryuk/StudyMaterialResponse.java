package com.ignit.internship.dto.belajaryuk;

import java.util.List;

import com.ignit.internship.model.belajaryuk.StudyMaterial;

public class StudyMaterialResponse {

    private String title;

    private Long thumbnailId;

    private List<Long> imageIds;

    private String content;

    private Long moduleId;

    public StudyMaterialResponse(
        String title, 
        Long thumbnailId, 
        List<Long> imageIds, 
        String content, 
        Long moduleId
    ) {
        this.title = title;
        this.thumbnailId = thumbnailId;
        this.imageIds = imageIds;
        this.content = content;
        this.moduleId = moduleId;
    }

    public StudyMaterialResponse(StudyMaterial material) {
        this(
            material.getTitle(),
            material.getThumbnailId(),
            material.getImageIds(),
            material.getContent(), 
            material.getModule().getId()
        );
    }

    public String getTitle() {
        return title;
    }

    public Long getThumbnailId() {
        return thumbnailId;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public String getContent() {
        return content;
    }

    public Long getModuleId() {
        return moduleId;
    }    
}
