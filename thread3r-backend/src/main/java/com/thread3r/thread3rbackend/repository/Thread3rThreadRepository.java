package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Thread3rThreadRepository extends JpaRepository<ThreadEntity, Long> {

    List<ThreadEntity> findByUserId(Long userId);
    List<ThreadEntity> findByGroupId(Long groupId);
    Optional<ThreadEntity> findById(Long id);
    boolean existsById(Long id);

}
