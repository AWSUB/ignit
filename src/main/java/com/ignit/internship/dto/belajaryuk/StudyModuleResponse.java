package com.ignit.internship.dto.belajaryuk;

import java.util.List;

import com.ignit.internship.model.belajaryuk.StudyModule;

public class StudyModuleResponse {

    private String title;

    private Long imageId;

    private String introduction;

    private List<Long> materialIds;

    private List<Long> excerciseIds;

    private Long studyPackageId;

    public StudyModuleResponse(
        String title, 
        Long imageId, 
        String introduction, 
        List<Long> materialIds,    
        List<Long> excerciseIds, 
        Long studyPackageId
    ) {
        this.title = title;
        this.imageId = imageId;
        this.introduction = introduction;
        this.materialIds = materialIds;
        this.excerciseIds = excerciseIds;
        this.studyPackageId = studyPackageId;
    }

    public StudyModuleResponse(StudyModule module) {
        this(
            module.getTitle(),
            module.getImageId(),
            module.getIntroduction(),
            module.getMaterials().stream().map(m -> m.getId()).toList(),
            module.getExcercises().stream().map(e -> e.getId()).toList(),
            module.getStudyPackage().getId()
        );
    }

    public String getTitle() {
        return title;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public List<Long> getMaterialIds() {
        return materialIds;
    }

    public List<Long> getExcerciseIds() {
        return excerciseIds;
    }

    public Long getStudyPackageId() {
        return studyPackageId;
    }
}
