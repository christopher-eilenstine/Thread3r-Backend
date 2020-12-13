package com.thread3r.thread3rbackend.service;

import com.thread3r.thread3rbackend.dto.GroupDto;
import com.thread3r.thread3rbackend.exception.Thread3rEntityExistsException;
import com.thread3r.thread3rbackend.exception.Thread3rNotFoundException;
import com.thread3r.thread3rbackend.exception.Thread3rUnauthorizedException;
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
                    .created(group.getCreatedTimestamp())
                    .creatorId(group.getUserId())
                    .creator(userService.getUsername(group.getUserId()))
                    .name(group.getName())
                    .description(group.getDescription())
                    .build();
            groups.add(groupDto);
        });
        return groups;
    }

    public List<GroupDto> getGroupsByCreator(Long userId) {
        List<GroupDto> groups = new ArrayList<>();
        groupRepository.findByUserId(userId).forEach(group -> {
            GroupDto groupDto = GroupDto.builder()
                    .id(group.getId())
                    .created(group.getCreatedTimestamp())
                    .creatorId(group.getUserId())
                    .creator(userService.getUsername(group.getUserId()))
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
                    .created(group.getCreatedTimestamp())
                    .creatorId(group.getUserId())
                    .creator(userService.getUsername(group.getUserId()))
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
                .created(group.getCreatedTimestamp())
                .creatorId(group.getUserId())
                .creator(userService.getUsername(group.getUserId()))
                .name(group.getName())
                .description(group.getDescription())
                .build();
    }

    public GroupDto createGroup(Long userId, GroupDto groupDto) {
        GroupEntity groupEntity = groupRepository.findByName(groupDto.getName()).orElse(null);
        if (groupEntity == null) {
            throw new Thread3rEntityExistsException();
        }
        groupEntity = GroupEntity.builder()
                .userId(userId)
                .name(groupDto.getName())
                .description(groupDto.getDescription())
                .members(new HashSet<>())
                .build();

        groupEntity.getMembers().add(userService.getUser(userId));
        groupRepository.save(groupEntity);

        return GroupDto.builder()
                .id(groupEntity.getId())
                .created(groupEntity.getCreatedTimestamp())
                .creatorId(groupEntity.getUserId())
                .creator(userService.getUsername(groupEntity.getUserId()))
                .name(groupEntity.getName())
                .description(groupEntity.getDescription())
                .build();
    }

    public void deleteGroup(Long groupId, Long userId) {
        GroupEntity group = findGroup(groupId);

        if (!group.getUserId().equals(userId)) {
            throw new Thread3rUnauthorizedException();
        }

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

    public void subscribeToGroup(Long groupId, Long userId) {
        GroupEntity groupEntity = groupRepository.findById(groupId).orElse(null);
        if (groupEntity == null) {
            throw new Thread3rNotFoundException();
        }
        groupEntity.getMembers().add(userService.getUser(userId));
        groupRepository.save(groupEntity);
    }

    // TODO: Add validation to prevent the creator of a group from unsubscribing from it
    public void unsubscribeFromGroup(Long groupId, Long userId) {
        GroupEntity groupEntity = groupRepository.findById(groupId).orElse(null);
        if (groupEntity == null) {
            throw new Thread3rNotFoundException();
        }
        groupEntity.getMembers().removeIf(member -> member.getId().equals(userId));
        groupRepository.save(groupEntity);
    }

}
