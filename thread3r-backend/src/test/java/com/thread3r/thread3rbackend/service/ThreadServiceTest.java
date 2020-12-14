package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.ThreadDto;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.model.GroupEntity;
import com.thread3r.thread3rbackend.model.ThreadEntity;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.repository.ThreadRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class ThreadServiceTest {

    @InjectMocks
    private ThreadService threadService;

    @Mock
    private ThreadRepository threadRepository;
    @Mock
    private UserService userService;

    private ThreadEntity threadEntity;
    private UserEntity userEntity;
    private GroupEntity groupEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        threadEntity = ThreadEntity.builder()
                .id(1L)
                .userId(1L)
                .groupId(1L)
                .title("Thread 1")
                .content("Thread 1 Content")
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .subscribed(new HashSet<>())
                .build();

        groupEntity = GroupEntity.builder()
                .id(1L)
                .userId(1L)
                .name("Group 1")
                .description("Group 1 Description")
                .build();

        userEntity.getSubscribed().add(groupEntity);
    }

    @Test
    public void testGetThreads() {
        Mockito.when(userService.getUser(1L)).thenReturn(userEntity);
        Mockito.when(userService.getUsername(1L)).thenReturn(userEntity.getUsername());
        Mockito.when(threadRepository.findByGroupId(1L)).thenReturn(Collections.singletonList(threadEntity));

        List<ThreadDto> threadDtos = threadService.getThreads(1L);

        Assert.assertNotNull(threadDtos);
        Assert.assertEquals(1, threadDtos.size());

        ThreadDto threadDto = threadDtos.get(0);

        Assert.assertEquals(1L, (long) threadDto.getId());
        Assert.assertEquals(1L, (long) threadDto.getGroup());
        Assert.assertEquals(1L, (long) threadDto.getCreatorId());
        Assert.assertEquals("user1", threadDto.getCreator());
        Assert.assertEquals("Thread 1", threadDto.getTitle());
        Assert.assertEquals("Thread 1 Content", threadDto.getContent());
    }

    @Test
    public void testGetThreadsByCreator() {
        Mockito.when(userService.getUsername(1L)).thenReturn(userEntity.getUsername());
        Mockito.when(threadRepository.findByUserId(1L)).thenReturn(Collections.singletonList(threadEntity));

        List<ThreadDto> threadDtos = threadService.getThreadsByCreator(1L);

        Assert.assertNotNull(threadDtos);
        Assert.assertEquals(1, threadDtos.size());

        ThreadDto threadDto = threadDtos.get(0);

        Assert.assertEquals(1L, (long) threadDto.getId());
        Assert.assertEquals(1L, (long) threadDto.getGroup());
        Assert.assertEquals(1L, (long) threadDto.getCreatorId());
        Assert.assertEquals("user1", threadDto.getCreator());
        Assert.assertEquals("Thread 1", threadDto.getTitle());
        Assert.assertEquals("Thread 1 Content", threadDto.getContent());
    }

    @Test
    public void testGetThreadsByGroup() {
        Mockito.when(userService.getUsername(1L)).thenReturn(userEntity.getUsername());
        Mockito.when(threadRepository.findByGroupId(1L)).thenReturn(Collections.singletonList(threadEntity));

        List<ThreadDto> threadDtos = threadService.getThreadsByGroup(1L);

        Assert.assertNotNull(threadDtos);
        Assert.assertEquals(1, threadDtos.size());

        ThreadDto threadDto = threadDtos.get(0);

        Assert.assertEquals(1L, (long) threadDto.getId());
        Assert.assertEquals(1L, (long) threadDto.getGroup());
        Assert.assertEquals(1L, (long) threadDto.getCreatorId());
        Assert.assertEquals("user1", threadDto.getCreator());
        Assert.assertEquals("Thread 1", threadDto.getTitle());
        Assert.assertEquals("Thread 1 Content", threadDto.getContent());
    }

    @Test
    public void testGetThreadByGroup() {
        Mockito.when(userService.getUsername(1L)).thenReturn(userEntity.getUsername());
        Mockito.when(threadRepository.findByGroupIdAndId(1L, 1L)).thenReturn(Optional.of(threadEntity));

        ThreadDto threadDto = threadService.getThreadByGroup(1L, 1L);

        Assert.assertNotNull(threadDto);
        Assert.assertEquals(1L, (long) threadDto.getId());
        Assert.assertEquals(1L, (long) threadDto.getGroup());
        Assert.assertEquals(1L, (long) threadDto.getCreatorId());
        Assert.assertEquals("user1", threadDto.getCreator());
        Assert.assertEquals("Thread 1", threadDto.getTitle());
        Assert.assertEquals("Thread 1 Content", threadDto.getContent());
    }

    @Test(expected = Thread3rNotFoundException.class)
    public void testGetThreadByGroupNotFound() {
        Mockito.when(threadRepository.findByGroupIdAndId(1L, 2L)).thenReturn(Optional.empty());

        threadService.getThreadByGroup(1L, 2L);
    }

}
