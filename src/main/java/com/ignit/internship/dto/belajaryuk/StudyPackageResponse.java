package com.ignit.internship.dto.belajaryuk;

import java.util.List;

import com.ignit.internship.model.belajaryuk.StudyPackage;

public class StudyPackageResponse {
    
    private String title;

    private String subtitle;

    private Long imageId;

    private Long price;

    private String tag;

    private List<Long> moduleIds;

    public StudyPackageResponse(
        String title, 
        String subtitle,
        Long imageId,
        Long price, 
        String tag,
        List<Long> moduleIds
    ) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageId = imageId;
        this.price = price;
        this.tag = tag;
        this.moduleIds = moduleIds;
    }

    public StudyPackageResponse(StudyPackage studyPackage) {
        this(
            studyPackage.getTitle(),
            studyPackage.getSubtitle(),
            studyPackage.getImageId(),
            studyPackage.getPrice(),
            studyPackage.getTag().getName(),
            studyPackage.getModules().stream().map(m -> m.getId()).toList()
        );
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Long getImageId() {
        return imageId;
    }

    public Long getPrice() {
        return price;
    }

    public String getTag() {
        return tag;
    }

    public List<Long> getModuleIds() {
        return moduleIds;
    }
}
