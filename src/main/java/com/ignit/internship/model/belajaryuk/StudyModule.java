package com.ignit.internship.model.belajaryuk;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class StudyModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long imageId;

    @Column(columnDefinition = "text")
    private String introduction;

    @OneToMany(mappedBy = "module", cascade = ALL, orphanRemoval = true)
    private List<StudyMaterial> materials;

    @OneToMany(mappedBy = "module", cascade = ALL, orphanRemoval = true)
    private List<StudyExercise> exercises;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private StudyPackage studyPackage;

    @SuppressWarnings("unused")
    private StudyModule() {}

    public StudyModule(
        String title, 
        String introduction,
        Long imageId,
        StudyPackage studyPackage
    ) {
        this.title = title;
        this.introduction = introduction;
        this.imageId = imageId;
        this.materials = new ArrayList<StudyMaterial>();
        this.exercises = new ArrayList<StudyExercise>();
        this.studyPackage = studyPackage;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getImageId() {
        return imageId;
    }
    
    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public List<StudyMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<StudyMaterial> materials) {
        this.materials = materials;
    }

    public List<StudyExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<StudyExercise> exercises) {
        this.exercises = exercises;
    }

    public StudyPackage getStudyPackage() {
        return studyPackage;
    }
}
