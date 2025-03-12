package com.ignit.internship.repository.belajaryuk;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyExcercise;
import com.ignit.internship.model.belajaryuk.StudyMaterial;
import com.ignit.internship.model.belajaryuk.StudyModule;

@Repository
public interface StudyModuleRepository extends JpaRepository<StudyModule, Long> {

    @Query("SELECT m FROM StudyMaterial m WHERE m.module.id = :moduleId AND m.id = :materialId")
    Optional<StudyMaterial> findStudyMaterialById(Long moduleId, Long materialId);

    @Query("SELECT e FROM StudyExcercise e WHERE e.module.id = :moduleId AND e.id = :materialId")
    Optional<StudyExcercise> findStudyExcerciseById(Long moduleId, Long excerciseId);
}
