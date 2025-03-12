package com.ignit.internship.repository.belajaryuk;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyMaterial;

@Repository
public interface StudyMaterialRespository extends JpaRepository<StudyMaterial, Long> {

    @Query("SELECT m FROM StudyMaterial m WHERE m.module.id = :moduleId AND m.id = :materialId")
    Optional<StudyMaterial> findByModuleIdAndMaterialId(Long moduleId, Long materialId);
}
