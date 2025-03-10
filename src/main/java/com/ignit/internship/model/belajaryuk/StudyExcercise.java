package com.ignit.internship.model.belajaryuk;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class StudyExcercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long thumbnailId;

    @ManyToOne(cascade = CascadeType.ALL)
    private StudyModule module;

    @SuppressWarnings("unused")
    private StudyExcercise() {}

    public StudyExcercise(Long thumbhnailId, StudyModule module) {
        this.thumbnailId = thumbhnailId;
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

    public StudyModule getModule() {
        return module;
    }
}
