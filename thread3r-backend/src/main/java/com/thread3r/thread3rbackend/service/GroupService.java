package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.GroupDto;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.model.GroupEntity;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserService userService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
    }

    public List<GroupDto> getGroups() {
        List<GroupDto> groups = new ArrayList<>();
        groupRepository.findAll().forEach(group -> {
            GroupDto groupDto = GroupDto.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .build();
            groups.add(groupDto);
        });
        return groups;
    }

    public List<GroupDto> getSubscribedGroups(Long userId) {
        List<GroupDto> groups = new ArrayList<>();
        UserEntity user = userService.getUser(userId);
        user.getSubscribed().forEach(group -> {
            GroupDto groupDto = GroupDto.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .build();
            groups.add(groupDto);
        });
        return groups;
    }

    public List<GroupDto> getGroupsByName(String name) {
        List<GroupDto> groups = new ArrayList<>();
        groupRepository.findByNameContaining(name).forEach(group -> {
            GroupDto groupDto = GroupDto.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .build();
            groups.add(groupDto);
        });
        return groups;
    }

    public GroupDto getGroup(Long groupId) {
        GroupEntity group = findGroup(groupId);
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .build();
    }

    public GroupDto createGroup(Long userId, GroupDto groupDto) {
        GroupEntity groupEntity = GroupEntity.builder()
                .userId(userId)
                .name(groupDto.getName())
                .description(groupDto.getDescription())
                .members(new HashSet<>())
                .build();

        groupEntity.getMembers().add(userService.getUser(userId));
        groupRepository.save(groupEntity);

        return GroupDto.builder()
                .id(groupEntity.getId())
                .name(groupEntity.getName())
                .description(groupEntity.getDescription())
                .build();
    }

    // TODO: Check userId to ensure creator is deleting.
    public void deleteGroup(Long groupId) {
        GroupEntity group = findGroup(groupId);

        group.getMembers().forEach(user -> group.getMembers().remove(user));
        groupRepository.save(group);

        groupRepository.deleteById(groupId);
    }

    private GroupEntity findGroup(Long groupId) {
        Optional<GroupEntity> groupEntity = groupRepository.findById(groupId);
        if (!groupEntity.isPresent()) {
            throw new Thread3rNotFoundException();
        }
        return groupEntity.get();
    }

}