package com.thread3r.thread3rbackend.controller;

import com.thread3r.thread3rbackend.dto.CommentDto;
import com.thread3r.thread3rbackend.security.UserDetailsImpl;
import com.thread3r.thread3rbackend.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/groups/threads/comments/created")
    public List<CommentDto> getCommentsByCreator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return commentService.getCommentsByCreator(userDetails.getId());
    }

    @GetMapping("/groups/threads/{threadId}/comments")
    public List<CommentDto> getCommentsByThread(@PathVariable Long threadId) {
        return commentService.getCommentsByThread(threadId);
    }

    @PostMapping("/groups/threads/{threadId}/comments")
    public CommentDto createComment(@PathVariable Long threadId, @Valid @RequestBody CommentDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return commentService.createComment(userDetails.getId(), threadId, request);
    }

    @DeleteMapping("/groups/threads/{threadId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long threadId, @PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        commentService.deleteComment(userDetails.getId(), threadId, commentId);
    }

}