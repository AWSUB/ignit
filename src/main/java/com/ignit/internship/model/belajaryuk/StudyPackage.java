package com.ignit.internship.model.belajaryuk;

import java.util.ArrayList;
import java.util.List;

import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.model.utils.Tag;

import static jakarta.persistence.CascadeType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class StudyPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subtitle;

    private Long imageId;

    private Long price;

    @ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private Tag tag;

    @OneToMany(mappedBy = "studyPackage", cascade = ALL, orphanRemoval = true)
    private List<StudyModule> modules;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<UserProfile> profiles;

    public StudyPackage(
        String title, 
        String subtitle, 
        Long imageId, 
        Long price, 
        Tag tag
    ) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageId = imageId;
        this.price = price;
        this.tag = tag;
        this.modules = new ArrayList<StudyModule>();
        this.profiles = new ArrayList<UserProfile>();
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public List<StudyModule> getModules() {
        return modules;
    }

    public void addModule(StudyModule module) {
        this.modules.add(module);
    }

    public List<UserProfile> getProfiles() {
        return profiles;
    }

    public void addProfile(UserProfile profile) {
        this.profiles.add(profile);
    }
}
