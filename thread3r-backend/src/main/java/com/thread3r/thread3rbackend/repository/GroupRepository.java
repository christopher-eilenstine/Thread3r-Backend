package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.GroupEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findById(Long id);
    Optional<GroupEntity> findByName(String name);
    List<GroupEntity> findByUserId(Long userId);
    Boolean existsByName(String name);
    boolean existsById(Long id);
    List<GroupEntity> findByNameContaining(String name);

}
