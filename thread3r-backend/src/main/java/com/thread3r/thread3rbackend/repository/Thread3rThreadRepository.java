package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.Thread3rThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Thread3rThreadRepository extends JpaRepository<Thread3rThread, Long> {

    List<Thread3rThread> findByUserId(Long userId);
    List<Thread3rThread> findByGroupId(Long groupId);
    Optional<Thread3rThread> findById(Long id);
    boolean existsById(Long id);

}
