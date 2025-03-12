package com.ignit.internship.model.belajaryuk;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class StudyExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long thumbnailId;

    @ElementCollection
    private List<Long> imageIds;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private StudyModule module;

    @SuppressWarnings("unused")
    private StudyExercise() {}

    public StudyExercise(
        String title, 
        Long thumbnailId,
        List<Long> imageIds, 
        String content, 
        StudyModule module
    ) {
        this.title = title;
        this.imageIds = imageIds;
        this.thumbnailId = thumbnailId;
        this.content = content;
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public Long getThumbnailId() {
        return thumbnailId;
    }

    public void setThumbnailId(Long thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Long> imageIds) {
        this.imageIds = imageIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }

    public StudyModule getModule() {
        return module;
    }
}
