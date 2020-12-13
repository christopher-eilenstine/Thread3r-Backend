package com.thread3r.thread3rbackend.controller;

import com.thread3r.thread3rbackend.dto.UserDto;
import com.thread3r.thread3rbackend.model.UserEntity;
import com.thread3r.thread3rbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        UserEntity user = userService.getUser(userId);
        return UserDto.builder()
                .id(user.getId())
                .created(user.getCreatedTimestamp())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

}
