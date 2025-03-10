package com.ignit.internship.dto.belajaryuk;

public class StudyModuleRequest {

    private String title;

    private String introduction;

    private Long packageId;

    public StudyModuleRequest(
        String title, 
        String introduction,
        Long packageId
    ) {
        this.title = title;
        this.introduction = introduction;
        this.packageId = packageId;
    }

    public String getTitle() {
        return title;
    }

    public Long getPackageId() {
        return packageId;
    }

    public String getIntroduction() {
        return introduction;
    }
}
