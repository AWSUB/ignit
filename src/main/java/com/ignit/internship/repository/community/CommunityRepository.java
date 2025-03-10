package com.ignit.internship.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.community.Community;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {}
