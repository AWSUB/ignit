package com.ignit.internship.repository.belajaryuk;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyExercise;
import com.ignit.internship.model.belajaryuk.StudyMaterial;
import com.ignit.internship.model.belajaryuk.StudyModule;

@Repository
public interface StudyModuleRepository extends JpaRepository<StudyModule, Long> {

    @Query("SELECT m FROM StudyModule m JOIN m.studyPackage.profiles p WHERE m.id = :moduleId AND p.id = :profileId")
    Optional<StudyModule> findByModuleIdAndProfileId(Long moduleId, Long profileId);

    @Query("SELECT mt FROM StudyMaterial mt WHERE mt.module.id = :moduleId")
    List<StudyMaterial> findAllStudyMaterialByModule(Long moduleId);

    @Query("SELECT e FROM StudyExercise e WHERE e.module.id = :moduleId")
    List<StudyExercise> findAllStudyExerciseByModule(Long moduleId);

    @Query("SELECT mt FROM StudyMaterial mt WHERE mt.module.id = :moduleId AND mt.id = :materialId")
    Optional<StudyMaterial> findStudyMaterialById(Long moduleId, Long materialId);

    @Query("SELECT e FROM StudyExercise e WHERE e.module.id = :moduleId AND e.id = :materialId")
    Optional<StudyExercise> findStudyExerciseById(Long moduleId, Long exerciseId);
}
