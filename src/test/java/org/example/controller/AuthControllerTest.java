package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.SecurityConfig;
import org.example.dto.UserDto;
import org.example.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUser() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("bulka");

        mockMvc.perform(post("/auth")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService).createUser(any(UserDto.class));
    }

    @Test
    void shouldSetUserRoleToUSER() throws Exception {
        UserDto user = new UserDto();
        user.setUsername("bulka");

        mockMvc.perform(post("/auth")
                        .with(csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService).createUser(argThat(dto ->
                dto.getRoles().contains("USER")
        ));
    }
}