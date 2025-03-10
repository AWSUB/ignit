package com.ignit.internship.repository.utils;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.utils.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {}
