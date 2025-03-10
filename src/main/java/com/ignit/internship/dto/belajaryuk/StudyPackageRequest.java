package com.ignit.internship.dto.belajaryuk;

public class StudyPackageRequest {

    private String title;

    private String subtitle;

    private Long price;

    private String tag;

    public StudyPackageRequest(
        String title, 
        String subtitle, 
        Long price, 
        String tag
    ) {
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Long getPrice() {
        return price;
    }

    public String getTag() {
        return tag;
    }
}
