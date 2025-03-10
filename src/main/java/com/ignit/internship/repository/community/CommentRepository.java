package com.ignit.internship.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ignit.internship.model.community.UserComment;
import com.ignit.internship.model.community.UserThread;

@Repository
public interface CommentRepository extends JpaRepository<UserComment, Long> {

    @Query("SELECT comment FROM UserComment comment WHERE comment.thread = :thread AND comment.parent IS NULL")
    List<UserComment> findAllByThread(UserThread thread);
}
