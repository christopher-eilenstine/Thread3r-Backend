package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.CommentDto;
import com.thread3r.thread3rbackend.model.CommentEntity;
import com.thread3r.thread3rbackend.repository.CommentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;

    private CommentEntity commentEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        commentEntity = CommentEntity.builder()
                .id(1L)
                .userId(1L)
                .threadId(1L)
                .content("Comment 1")
                .build();
    }

    @Test
    public void testGetCommentsByCreator() {
        Mockito.when(commentRepository.findByUserId(1L)).thenReturn(Collections.singletonList(commentEntity));

        List<CommentDto> commentDtos = commentService.getCommentsByCreator(1L);

        Assert.assertNotNull(commentDtos);
        Assert.assertEquals(1, commentDtos.size());

        CommentDto commentDto = commentDtos.get(0);

        Assert.assertEquals(1L, (long) commentDto.getId());
        Assert.assertEquals(1L, (long) commentDto.getThread());
        Assert.assertEquals(1L, (long) commentDto.getCreatorId());
        Assert.assertEquals("Comment 1", commentDto.getContent());
    }

    @Test
    public void testGetCommentsByThread() {
        Mockito.when(commentRepository.findByThreadId(1L)).thenReturn(Collections.singletonList(commentEntity));

        List<CommentDto> commentDtos = commentService.getCommentsByThread(1L);

        Assert.assertNotNull(commentDtos);
        Assert.assertEquals(1, commentDtos.size());

        CommentDto commentDto = commentDtos.get(0);

        Assert.assertEquals(1L, (long) commentDto.getId());
        Assert.assertEquals(1L, (long) commentDto.getThread());
        Assert.assertEquals(1L, (long) commentDto.getCreatorId());
        Assert.assertEquals("Comment 1", commentDto.getContent());
    }

}
