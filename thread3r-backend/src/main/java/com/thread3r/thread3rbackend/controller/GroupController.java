package com.thread3r.thread3rbackend.controller;

import com.thread3r.thread3rbackend.dto.GroupDto;
import com.thread3r.thread3rbackend.model.GroupEntity;
import com.thread3r.thread3rbackend.repository.Thread3rGroupRepository;
import com.thread3r.thread3rbackend.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = {"http://localhost:3000"})
public class GroupController {

    private final Thread3rGroupRepository groupRepository;

    @Autowired
    public GroupController(Thread3rGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /*
    @GetMapping
    public ResponseEntity<?> getGroups() {
        return ResponseEntity.ok(groupRepository.findByUserId(6L));
    }
    */

    @PostMapping
    public ResponseEntity<?> createGroup(@Valid @RequestBody GroupDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        GroupEntity group = GroupEntity.builder()
                .userId(userDetails.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return ResponseEntity.ok(groupRepository.save(group));
    }

}
