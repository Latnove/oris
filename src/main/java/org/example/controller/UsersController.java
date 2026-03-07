package org.example.controller;

import org.example.dto.UserDto;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> users() {

        List<UserDto> users = userService.findAll();

        return users;
    }

    @PostMapping("/users")
    public UserDto indexPost(@RequestParam("username") String username) {

        UserDto user = new UserDto();
        user.setUsername(username);

        UserDto userDto = userService.save(user);

        return userDto;
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto dto) {
        try {

            if (dto.getId() == null || dto.getUsername() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(userService.updateUsername(dto));

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{username}")
    public UserDto delete(@PathVariable("username") String username) {
        return userService.deleteByUsername(username);
    }
}
