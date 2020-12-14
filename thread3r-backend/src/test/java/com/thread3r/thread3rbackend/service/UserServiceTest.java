package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        userEntity = UserEntity.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .subscribed(new HashSet<>())
                .build();
    }

    @Test
    public void testGetUser() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserEntity userEntity = userService.getUser(1L);

        Assert.assertNotNull(userEntity);
        Assert.assertEquals(1L, (long) userEntity.getId());
        Assert.assertEquals("user1", userEntity.getUsername());
        Assert.assertEquals("user1@test.com", userEntity.getEmail());
    }

    @Test(expected = Thread3rNotFoundException.class)
    public void testGetUserNotFound() {
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());

        userService.getUser(2L);
    }

}
