package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.ThreadDto;
import com.thread3r.thread3rbackend.exception.Thread3rEntityExistsException;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.exception.Thread3rUnauthorizedException;
import com.thread3r.thread3rbackend.model.ThreadEntity;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.repository.CommentRepository;
import com.thread3r.thread3rbackend.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final GroupService groupService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Autowired
    public ThreadService(ThreadRepository threadRepository, GroupService groupService, UserService userService, CommentRepository commentRepository) {
        this.threadRepository = threadRepository;
        this.groupService = groupService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    // GET /threads
    // Retrieve a list of threads for the current user.
    public List<ThreadDto> getThreadsByUser(Long userId) {
        List<ThreadDto> threads = new ArrayList<>();

        threadRepository.findByUserId(userId).forEach(thread -> {
            ThreadDto threadDto = ThreadDto.builder()
                    .id(thread.getId())
                    .title(thread.getTitle())
                    .content(thread.getContent())
                    .build();
            threads.add(threadDto);
        });
        return threads;
    }

    // GET /threads/{groupId}
    // Retrieve a list of threads for an existing group, or retrieves a single thread if an id is specified.
    public List<ThreadDto> getThreadsByGroup(Long groupId) {
        List<ThreadDto> threads = new ArrayList<>();
        threadRepository.findByGroupId(groupId).forEach(thread -> {
            ThreadDto threadDto = ThreadDto.builder()
                    .id(thread.getId())
                    .title(thread.getTitle())
                    .content(thread.getContent())
                    .build();
            threads.add(threadDto);
        });
        return threads;
    }

    public ThreadDto getThreadById(Long threadId) {
        if (!threadRepository.existsById(threadId)) {
            throw new Thread3rEntityExistsException();
        }

        ThreadEntity threadEntity = findThread(threadId);

        return ThreadDto.builder()
                .id(threadEntity.getId())
                .title(threadEntity.getTitle())
                .content(threadEntity.getContent())
                .build();
    }

    // POST /threads/{groupId}
    // Create a new thread in an existing group.
    public ThreadDto createThread(Long userId, Long groupId, ThreadDto threadDto) {
        ThreadEntity threadEntity;
        threadEntity = ThreadEntity.builder()
                .groupId(groupId)
                .userId(userId)
                .title(threadDto.getTitle())
                .content(threadDto.getContent())
                .build();

        threadRepository.save(threadEntity);

        return ThreadDto.builder()
                .id(threadEntity.getId())
                .title(threadEntity.getTitle())
                .content(threadEntity.getContent())
                .build();
    }

    //TODO?:
    // PUT /threads/update/{threadId}
    // Update an existing thread.

    // DELETE /threads/delete/{threadId}
    // Delete an existing thread.
    public void deleteThread(Long threadId, Long userId) {
        ThreadEntity thread = findThread(threadId);

        if (thread.getUserId().equals(userId)) {
            // Delete associated comments.
            commentRepository.findByThreadId(threadId).forEach(comment -> commentRepository.deleteById(comment.getId()));

            threadRepository.deleteById(threadId);
        }
        else {
            throw new Thread3rUnauthorizedException();
        }

    }

    private ThreadEntity findThread(Long threadId) {
        Optional<ThreadEntity> threadEntity = threadRepository.findById(threadId);
        if (!threadEntity.isPresent()) {
            throw new Thread3rNotFoundException();
        }
        return threadEntity.get();
    }
}