package com.thread3r.thread3rbackend.repository;

import com.thread3r.thread3rbackend.model.Thread3rUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Thread3rUserRepository extends JpaRepository<Thread3rUser, Long> {

    Optional<Thread3rUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}
