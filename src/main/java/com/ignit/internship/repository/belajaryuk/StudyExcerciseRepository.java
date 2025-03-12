package com.ignit.internship.repository.belajaryuk;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyExcercise;

@Repository
public interface StudyExcerciseRepository extends JpaRepository<StudyExcercise,Long> {

    @Query("SELECT e FROM StudyExcercise e WHERE e.module.id = :moduleId AND e.id = :materialId")
    Optional<StudyExcercise> findByModuleIdAndExcerciseId(Long moduleId, Long excerciseId);
}
