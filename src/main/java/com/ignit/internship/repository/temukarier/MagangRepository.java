package com.ignit.internship.repository.temukarier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.temukarier.Magang;

@Repository
public interface MagangRepository extends JpaRepository<Magang, Long> {

    @Query("SELECT m FROM Magang m JOIN m.tags t WHERE t.name = :tag")
    Page<Magang> findByTagName(@Param("tag") String tag, Pageable pageable);
}
