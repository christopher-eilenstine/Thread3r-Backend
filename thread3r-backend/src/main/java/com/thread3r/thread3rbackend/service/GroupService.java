package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.GroupDto;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.model.GroupEntity;
import com.thread3r.thread3rbackend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public GroupDto getGroup(Long groupId) {
        GroupEntity group = findGroup(groupId);
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
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
