package com.thread3r.thread3rbackend.controller;

import com.thread3r.thread3rbackend.dto.ThreadDto;
import com.thread3r.thread3rbackend.security.UserDetailsImpl;
import com.thread3r.thread3rbackend.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/groups/threads")
    public List<ThreadDto> getThreads() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return threadService.getThreads(userDetails.getId());
    }

    @GetMapping("/groups/threads/created")
    public List<ThreadDto> getThreadsByCreator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return threadService.getThreadsByCreator(userDetails.getId());
    }

    @GetMapping("/groups/{groupId}/threads")
    public List<ThreadDto> getThreadsByGroup(@PathVariable Long groupId) {
        return threadService.getThreadsByGroup(groupId);
    }

    @PostMapping("/groups/{groupId}/threads")
    public ThreadDto createThread(@PathVariable Long groupId, @Valid @RequestBody ThreadDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return threadService.createThread(userDetails.getId(), groupId, request);
    }

    @GetMapping("/groups/{groupId}/threads/{threadId}")
    public ThreadDto getThread(@PathVariable Long groupId, @PathVariable Long threadId) {
        return threadService.getThreadByGroup(groupId, threadId);
    }

    @DeleteMapping("/groups/{groupId}/threads/{threadId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThread(@PathVariable Long groupId, @PathVariable Long threadId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        threadService.deleteThread(groupId, threadId, userDetails.getId());
    }

    //TODO?:
    // PUT /threads/update/{threadId}
    // Update an existing thread.
}
