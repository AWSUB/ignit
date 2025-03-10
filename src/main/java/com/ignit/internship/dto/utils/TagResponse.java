package com.ignit.internship.dto.utils;

import com.ignit.internship.model.utils.Tag;

public class TagResponse {

    private String name;

    public TagResponse(String name) {
        this.name = name;
    }

    public TagResponse(Tag tag) {
        this(tag.getName());
    }

    public String getName() {
        return name;
    }
}
