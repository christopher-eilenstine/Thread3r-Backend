package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Thread3rCommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByUserId(Long userId);
    List<CommentEntity> findByThreadId(Long threadId);
    boolean existsById(Long id);

}
