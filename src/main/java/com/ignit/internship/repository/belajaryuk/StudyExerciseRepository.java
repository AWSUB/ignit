package com.ignit.internship.repository.belajaryuk;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyExercise;

@Repository
public interface StudyExerciseRepository extends JpaRepository<StudyExercise,Long> {

    @Query("SELECT e FROM StudyExercise e WHERE e.module.id = :moduleId AND e.id = :materialId")
    Optional<StudyExercise> findByModuleIdAndExerciseId(Long moduleId, Long exerciseId);
}
