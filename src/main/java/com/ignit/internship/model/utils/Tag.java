package com.ignit.internship.model.utils;

import com.ignit.internship.model.cakrawala.Info;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class Tag {

    @Id
    private String name;

    @OneToOne(mappedBy = "tag", cascade = CascadeType.ALL, optional = true)
    @PrimaryKeyJoinColumn
    private Info info;

    @SuppressWarnings("unused")
    private Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
