package com.ignit.internship.repository.belajaryuk;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.belajaryuk.StudyPackage;

@Repository
public interface StudyPackageRepository extends JpaRepository<StudyPackage, Long> {

    @Query("SELECT s FROM StudyPackage s WHERE s.tag = :name")
    Page<StudyPackage> findByTagName(@Param("tag") String tag, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM StudyPackage s JOIN s.profiles p WHERE s.id = :packageId AND p.id = :profileId")
    boolean existsByProfileIdAndPackageId(Long profileId, Long packageId);
}
