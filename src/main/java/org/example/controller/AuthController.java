package org.example.controller;

import org.example.dto.UserDto;
import org.example.service.impl.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> indexPost(@RequestBody UserDto user) {
        user.setRoles(List.of("USER"));
        userService.createUser(user);
        return ResponseEntity.ok().build();

    }
}
