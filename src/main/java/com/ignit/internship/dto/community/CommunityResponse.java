package com.ignit.internship.dto.community;

import java.util.List;

import com.ignit.internship.model.community.Community;

public class CommunityResponse {
    private String title;

    private String content;

    private String url;

    private List<String> tags;

    public CommunityResponse(String title, String content, String url, List<String> tags) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.tags = tags;
    }

    public CommunityResponse(Community community) {
        this(
            community.getTitle(),
            community.getContent(),
            community.getUrl(),
            community.getTags().stream().map(t -> t.getName()).toList()
        );
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getTags() {
        return tags;
    }
}
