package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Optional<CommentEntity> findByIdAndThreadId(Long id, Long threadId);
    List<CommentEntity> findByUserId(Long userId);
    List<CommentEntity> findByThreadId(Long threadId);
    boolean existsById(Long id);

}
