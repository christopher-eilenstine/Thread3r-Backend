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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/threads")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ThreadController {

    private final ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }

    // GET /threads
    // Retrieve a list of threads for the current user.
    @GetMapping
    public List<ThreadDto> getThreadsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return threadService.getThreadsByUser(userDetails.getId());
    }

    // GET /threads/{groupId}
    // Retrieve a list of threads for an existing group, or retrieves a single thread if an id is specified.
    @GetMapping("/{groupId}")
    public List<ThreadDto> getThreadsByGroup(@PathVariable Long groupId, @RequestParam(value = "threadId", required = false) Long threadId) {
        if (threadId == null) {
            ThreadDto thread = threadService.getThreadById(threadId);
            List<ThreadDto> threads = new ArrayList<>();
            threads.add(thread);
            return threads;
        }
        return threadService.getThreadsByGroup(groupId);
    }

    // POST /threads/{groupId}
    // Create a new thread in an existing group.
    @PostMapping("/{groupId}")
    public ThreadDto createThread(@PathVariable Long groupId, @Valid @RequestBody ThreadDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return threadService.createThread(userDetails.getId(), groupId, request);
    }

    //TODO?:
    // PUT /threads/update/{threadId}
    // Update an existing thread.

    // DELETE /threads/delete/{threadId}
    // Delete an existing thread.
    @DeleteMapping("/delete/{threadId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteThread(@PathVariable Long threadId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        threadService.deleteThread(threadId, userDetails.getId());
    }

}
