package com.ignit.internship.repository.temukarier;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.temukarier.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p JOIN p.tags t WHERE t.name IN :tags GROUP BY p HAVING COUNT(t) = :tagCount")
    Page<Project> findByMultipleTagName(@Param("tags") List<String> tags, Integer tagCount, Pageable pageable);
}
