package com.ignit.internship.model.community;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserThread> threads;

    @SuppressWarnings("unused")
    private Community() {}

    public Community(String title, String content) {
        this.title = title;
        this.content = content;
        this.threads = new ArrayList<UserThread>();
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

    public List<UserThread> getThreads() {
        return threads;
    }

    public void addThread(UserThread thread) {
        this.threads.add(thread);
    }
}
