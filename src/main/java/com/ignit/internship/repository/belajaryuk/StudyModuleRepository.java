package com.ignit.internship.repository.belajaryuk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyModule;

@Repository
public interface StudyModuleRepository extends JpaRepository<StudyModule, Long> {}
