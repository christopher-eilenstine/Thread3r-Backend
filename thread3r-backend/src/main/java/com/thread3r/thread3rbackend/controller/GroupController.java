package com.thread3r.thread3rbackend.controller;

import com.thread3r.thread3rbackend.dto.GroupDto;
import com.thread3r.thread3rbackend.security.UserDetailsImpl;
import com.thread3r.thread3rbackend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = {"http://localhost:3000"})
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDto> getGroups() {
        return groupService.getGroups();
    }

    @PostMapping
    public GroupDto createGroup(@Valid @RequestBody GroupDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return groupService.createGroup(userDetails.getId(), request);
    }

    @GetMapping("/{groupId}")
    public GroupDto getGroup(@PathVariable Long groupId) {
        return groupService.getGroup(groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        groupService.deleteGroup(groupId, userDetails.getId());
    }

    @PostMapping("/{groupId}/subscribe")
    @ResponseStatus(HttpStatus.OK)
    public void subscribe(@PathVariable Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        groupService.subscribeToGroup(groupId, userDetails.getId());
    }

    @PostMapping("/{groupId}/unsubscribe")
    @ResponseStatus(HttpStatus.OK)
    public void unsubscribe(@PathVariable Long groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        groupService.unsubscribeFromGroup(groupId, userDetails.getId());
    }

    @GetMapping("/subscribed")
    public List<GroupDto> getSubscribedGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return groupService.getSubscribedGroups(userDetails.getId());
    }

    @GetMapping("/created")
    public List<GroupDto> getCreatedGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return groupService.getGroupsByCreator(userDetails.getId());
    }

}
