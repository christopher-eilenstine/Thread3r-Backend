package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.Thread3rGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Thread3rGroupRepository extends JpaRepository<Thread3rGroup, Long> {

    Optional<Thread3rGroup> findById(Long id);
    List<Thread3rGroup> findByUserId(Long userId);
    Boolean existsByName(String name);
    boolean existsById(Long id);
    Optional<Thread3rGroup> findByName(String name);

}
