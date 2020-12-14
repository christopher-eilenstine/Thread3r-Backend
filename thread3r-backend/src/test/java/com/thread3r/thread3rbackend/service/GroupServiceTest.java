package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.GroupDto;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.model.GroupEntity;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.repository.GroupRepository;
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

public class GroupServiceTest {

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserService userService;

    private GroupEntity groupEntity;
    private UserEntity userEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        groupEntity = GroupEntity.builder()
                .id(1L)
                .userId(1L)
                .name("Group 1")
                .description("Group 1 Description")
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .subscribed(new HashSet<>())
                .build();
        userEntity.getSubscribed().add(groupEntity);
    }

    @Test
    public void testGetGroups() {
        Mockito.when(groupRepository.findAll()).thenReturn(Collections.singletonList(groupEntity));

        List<GroupDto> groupDtos = groupService.getGroups();

        Assert.assertNotNull(groupDtos);
        Assert.assertEquals(1, groupDtos.size());

        GroupDto groupDto = groupDtos.get(0);

        Assert.assertEquals(1L, (long) groupDto.getId());
        Assert.assertEquals(1L, (long) groupDto.getCreatorId());
        Assert.assertEquals("Group 1", groupDto.getName());
        Assert.assertEquals("Group 1 Description", groupDto.getDescription());
    }

    @Test
    public void testGetGroupsByCreator() {
        Mockito.when(groupRepository.findByUserId(1L)).thenReturn(Collections.singletonList(groupEntity));
        Mockito.when(groupRepository.findByUserId(2L)).thenReturn(Collections.emptyList());

        List<GroupDto> groupDtos = groupService.getGroupsByCreator(1L);

        Assert.assertNotNull(groupDtos);
        Assert.assertEquals(1, groupDtos.size());

        GroupDto groupDto = groupDtos.get(0);

        Assert.assertEquals(1L, (long) groupDto.getId());
        Assert.assertEquals(1L, (long) groupDto.getCreatorId());
        Assert.assertEquals("Group 1", groupDto.getName());
        Assert.assertEquals("Group 1 Description", groupDto.getDescription());

        groupDtos = groupService.getGroupsByCreator(2L);

        Assert.assertNotNull(groupDtos);
        Assert.assertEquals(0, groupDtos.size());
    }

    @Test
    public void testGetSubscribedGroups() {
        Mockito.when(userService.getUser(1L)).thenReturn(userEntity);

        List<GroupDto> groupDtos = groupService.getSubscribedGroups(1L);

        Assert.assertNotNull(groupDtos);
        Assert.assertEquals(1, groupDtos.size());

        GroupDto groupDto = groupDtos.get(0);

        Assert.assertEquals(1L, (long) groupDto.getId());
        Assert.assertEquals(1L, (long) groupDto.getCreatorId());
        Assert.assertEquals("Group 1", groupDto.getName());
        Assert.assertEquals("Group 1 Description", groupDto.getDescription());
    }

    @Test
    public void testGetGroup() {
        Mockito.when(groupRepository.findById(1L)).thenReturn(Optional.of(groupEntity));

        GroupDto groupDto = groupService.getGroup(1L);

        Assert.assertNotNull(groupDto);
        Assert.assertEquals(1L, (long) groupDto.getId());
        Assert.assertEquals(1L, (long) groupDto.getCreatorId());
        Assert.assertEquals("Group 1", groupDto.getName());
        Assert.assertEquals("Group 1 Description", groupDto.getDescription());
    }

    @Test(expected = Thread3rNotFoundException.class)
    public void testGetGroupNotFound() {
        Mockito.when(groupRepository.findById(2L)).thenReturn(Optional.empty());

        GroupDto groupDto = groupService.getGroup(2L);
    }

}
