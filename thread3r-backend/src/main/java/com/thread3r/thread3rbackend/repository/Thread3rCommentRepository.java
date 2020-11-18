package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.Thread3rComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Thread3rCommentRepository extends JpaRepository<Thread3rComment, Long> {

    List<Thread3rComment> findByUserId(Long userId);
    List<Thread3rComment> findByThreadId(Long threadId);
    boolean existsById(Long id);

}
