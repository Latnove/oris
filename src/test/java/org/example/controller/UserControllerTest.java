package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.UserDto;
import org.example.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUsers() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("bulka");
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("sdfsdfsdf");
        userDto.setRoles(List.of("USER"));

        when(userService.findAll()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("bulka").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].username").value("bulka"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("bulka");
        user.setRoles(List.of("USER"));

        when(userService.save(any(UserDto.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .with(user("bulka").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("bulka"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setUsername("newName");

        when(userService.updateUsername(any(UserDto.class))).thenReturn(user);

        mockMvc.perform(put("/")
                        .with(user("bulka").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newName"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidData() throws Exception {
        UserDto user = new UserDto();

        mockMvc.perform(put("/")
                        .with(user("bulka").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn500WhenException() throws Exception {
        when(userService.updateUsername(any()))
                .thenThrow(new DataIntegrityViolationException("error"));

        UserDto user = new UserDto();
        user.setId(1L);
        user.setUsername("fail");

        mockMvc.perform(put("/")
                        .with(user("bulka").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("bulka");

        when(userService.deleteByUsername("bulka")).thenReturn(user);

        mockMvc.perform(delete("/bulka")
                        .with(user("bulka").roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("bulka"));
    }

}