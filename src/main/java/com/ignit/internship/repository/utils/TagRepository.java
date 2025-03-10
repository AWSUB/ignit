package com.ignit.internship.repository.utils;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.utils.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    
    default boolean existsAllById(List<String> ids) {
        return findAllById(ids).size() == ids.size();
    }
}
