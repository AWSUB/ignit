package com.ignit.internship.model.community;

import java.util.List;

import com.ignit.internship.model.utils.Tag;

import static jakarta.persistence.CascadeType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String url;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Tag> tags;

    public Community(String title, String content, String url, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.tags = tags;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
