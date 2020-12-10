package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.ThreadDto;
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
import java.util.Optional;

@Service
public class ThreadService {

    private final ThreadRepository threadRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public ThreadService(ThreadRepository threadRepository, CommentRepository commentRepository, UserService userService) {
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public List<ThreadDto> getThreads(Long userId) {
        List<ThreadDto> threads = new ArrayList<>();
        UserEntity user = userService.getUser(userId);
        user.getSubscribed().forEach(group ->
                threadRepository.findByGroupId(group.getId()).forEach(thread -> {
                   ThreadDto threadDto = ThreadDto.builder()
                           .id(thread.getId())
                           .created(thread.getCreatedTimestamp())
                           .creator(thread.getUserId())
                           .group(thread.getGroupId())
                           .title(thread.getTitle())
                           .content(thread.getContent())
                           .build();
                   threads.add(threadDto);
            })
        );
        return threads;
    }

    public List<ThreadDto> getThreadsByCreator(Long userId) {
        List<ThreadDto> threads = new ArrayList<>();
        threadRepository.findByUserId(userId).forEach(thread -> {
            ThreadDto threadDto = ThreadDto.builder()
                    .id(thread.getId())
                    .created(thread.getCreatedTimestamp())
                    .creator(thread.getUserId())
                    .group(thread.getGroupId())
                    .title(thread.getTitle())
                    .content(thread.getContent())
                    .build();
            threads.add(threadDto);
        });
        return threads;
    }

    public List<ThreadDto> getThreadsByGroup(Long groupId) {
        List<ThreadDto> threads = new ArrayList<>();
        threadRepository.findByGroupId(groupId).forEach(thread -> {
            ThreadDto threadDto = ThreadDto.builder()
                    .id(thread.getId())
                    .created(thread.getCreatedTimestamp())
                    .creator(thread.getUserId())
                    .group(thread.getGroupId())
                    .title(thread.getTitle())
                    .content(thread.getContent())
                    .build();
            threads.add(threadDto);
        });
        return threads;
    }

    public ThreadDto getThreadByGroup(Long groupId, Long threadId) {
        ThreadEntity threadEntity = findThread(groupId, threadId);

        return ThreadDto.builder()
                .id(threadEntity.getId())
                .created(threadEntity.getCreatedTimestamp())
                .creator(threadEntity.getUserId())
                .group(threadEntity.getGroupId())
                .title(threadEntity.getTitle())
                .content(threadEntity.getContent())
                .build();
    }

    public ThreadDto createThread(Long userId, Long groupId, ThreadDto threadDto) {
        ThreadEntity threadEntity = ThreadEntity.builder()
                .groupId(groupId)
                .userId(userId)
                .title(threadDto.getTitle())
                .content(threadDto.getContent())
                .build();

        threadRepository.save(threadEntity);

        return ThreadDto.builder()
                .id(threadEntity.getId())
                .created(threadEntity.getCreatedTimestamp())
                .creator(threadEntity.getUserId())
                .group(threadEntity.getGroupId())
                .title(threadEntity.getTitle())
                .content(threadEntity.getContent())
                .build();
    }

    //TODO?:
    // PUT /threads/update/{threadId}
    // Update an existing thread.

    public void deleteThread(Long groupId, Long threadId, Long userId) {
        ThreadEntity thread = findThread(groupId, threadId);

        if (!thread.getUserId().equals(userId)) {
            throw new Thread3rUnauthorizedException();
        }

        commentRepository.findByThreadId(threadId).forEach(comment -> commentRepository.deleteById(comment.getId()));
        threadRepository.deleteById(threadId);
    }

    private ThreadEntity findThread(Long groupId, Long threadId) {
        Optional<ThreadEntity> threadEntity = threadRepository.findByGroupIdAndId(groupId, threadId);
        if (!threadEntity.isPresent()) {
            throw new Thread3rNotFoundException();
        }
        return threadEntity.get();
    }
}