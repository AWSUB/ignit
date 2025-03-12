package com.ignit.internship.service.utils;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ignit.internship.model.utils.Tag;
import com.ignit.internship.repository.utils.TagRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(String name) {
        return tagRepository.save(new Tag(name));
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }

    public void deleteTag(String name) {
        tagRepository.deleteById(name);
    }
}
