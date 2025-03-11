package com.ignit.internship.repository.temukarier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.temukarier.Bootcamp;

@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {

    @Query("SELECT b FROM Bootcamp b JOIN b.tags t WHERE t.name IN :tags GROUP BY b HAVING COUNT(t) = :tagCount")
    Page<Bootcamp> findByMultipleTagName(@Param("tags") List<String> tags, Integer tagCount, Pageable pageable);
}
