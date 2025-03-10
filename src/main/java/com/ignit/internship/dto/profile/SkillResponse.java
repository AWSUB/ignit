package com.ignit.internship.dto.profile;

import com.ignit.internship.model.profile.Skill;

public class SkillResponse {

    private Long id;

    private String name;

    private String description;

    private Integer year;

    public SkillResponse(Long id, String name, String description, Integer year) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
    }

    public SkillResponse(Skill skill) {
        this(
            skill.getId(),
            skill.getName(),
            skill.getDescription(),
            skill.getYear()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getYear() {
        return year;
    }
}
