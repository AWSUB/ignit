package com.ignit.internship.dto.temukarier;

import java.util.List;

public class MagangRequest {

    private String name;

    private String description;

    private String url;

    private List<String> tags;

    public MagangRequest(
        String name, 
        String description, 
        String url,
        List<String> tags
    ) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getTags() {
        return tags;
    }
}
