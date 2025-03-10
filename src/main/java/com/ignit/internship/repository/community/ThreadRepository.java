package com.ignit.internship.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.community.Community;
import com.ignit.internship.model.community.UserThread;

@Repository
public interface ThreadRepository extends JpaRepository<UserThread, Long> {
    List<UserThread> findAllByCommunity(Community community);
}
