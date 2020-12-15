package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.CommentDto;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.exception.Thread3rUnauthorizedException;
import com.thread3r.thread3rbackend.model.CommentEntity;
import com.thread3r.thread3rbackend.model.ThreadEntity;
import com.thread3r.thread3rbackend.repository.CommentRepository;
import com.thread3r.thread3rbackend.repository.ThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ThreadRepository threadRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, ThreadRepository threadRepository) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.threadRepository = threadRepository;
    }

    public List<CommentDto> getCommentsByCreator(Long userId) {
        List<CommentDto> comments = new ArrayList<>();
        commentRepository.findByUserId(userId).forEach(comment -> {
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .created(comment.getCreatedTimestamp())
                    .thread(comment.getThreadId())
                    .group(comment.getGroupId())
                    .creatorId(comment.getUserId())
                    .creator(userService.getUsername(comment.getUserId()))
                    .content(comment.getContent())
                    .build();
            comments.add(commentDto);
        });
        return comments;
    }

    public List<CommentDto> getCommentsByThread(Long threadId) {
        List<CommentDto> comments = new ArrayList<>();
        commentRepository.findByThreadId(threadId).forEach(comment -> {
            CommentDto commentDto = CommentDto.builder()
                    .id(comment.getId())
                    .created(comment.getCreatedTimestamp())
                    .thread(comment.getThreadId())
                    .group(comment.getGroupId())
                    .creatorId(comment.getUserId())
                    .creator(userService.getUsername(comment.getUserId()))
                    .content(comment.getContent())
                    .build();
            comments.add(commentDto);
        });
        return comments;
    }

    public CommentDto createComment(Long userId, Long threadId, CommentDto commentDto) {
        ThreadEntity threadEntity = threadRepository.findById(threadId).orElse(null);
        if (threadEntity == null) {
            throw new Thread3rNotFoundException();
        }
        CommentEntity commentEntity = CommentEntity.builder()
                .userId(userId)
                .threadId(threadId)
                .groupId(threadEntity.getGroupId())
                .content(commentDto.getContent())
                .build();

        commentRepository.save(commentEntity);

        return CommentDto.builder()
                .id(commentEntity.getId())
                .created(commentEntity.getCreatedTimestamp())
                .creatorId(commentEntity.getUserId())
                .creator(userService.getUsername(commentEntity.getUserId()))
                .thread(commentEntity.getThreadId())
                .group(commentEntity.getGroupId())
                .content(commentEntity.getContent())
                .build();
    }

    public void deleteComment(Long userId, Long threadId, Long commentId) {
        Optional<CommentEntity> comment = commentRepository.findByIdAndThreadId(commentId, threadId);
        if (!comment.isPresent()) {
            throw new Thread3rNotFoundException();
        }
        if (!comment.get().getUserId().equals(userId)) {
            throw new Thread3rUnauthorizedException();
        }
        commentRepository.deleteById(commentId);
    }

}
