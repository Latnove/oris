package org.example.service.impl;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.example.config.properties.MailProperties;
import org.example.dto.UserDto;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MailProperties mailProperties;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("test");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> result = userService.findAll();

        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getUsername());
    }

    @Test
    void shouldFindUserByUsername() {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.of(user));

        User result = userService.findByUsername("test");

        assertEquals("test", result.getUsername());
    }

    @Test
    void shouldCreateUserAndSendMail() throws Exception {
        UserDto dto = new UserDto();
        dto.setUsername("test");
        dto.setPassword("123");
        dto.setEmail("test@mail.com");
        dto.setRoles(List.of("USER"));

        when(roleRepository.findAllByNameIn(any()))
                .thenReturn(List.of(new Role()));

        when(mailSender.createMimeMessage())
                .thenReturn(new MimeMessage((Session) null));

        when(mailProperties.getSubject()).thenReturn("Verify");
        when(mailProperties.getContent()).thenReturn("Hello $name $url");
        when(mailProperties.getBaseUrl()).thenReturn("http://localhost");
        when(mailProperties.getFrom()).thenReturn("test@mail.com");
        when(mailProperties.getSender()).thenReturn("Test");

        userService.createUser(dto);

        verify(userRepository).save(any(User.class));
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    void shouldVerifyUser() {
        User user = new User();
        user.setEnabled(false);

        when(userRepository.getByVerificationCode("code"))
                .thenReturn(Optional.of(user));

        boolean result = userService.verifyUser("code");

        assertTrue(result);
        assertTrue(user.isEnabled());
        verify(userRepository).save(user);
    }

    @Test
    void shouldReturnFalseIfUserNotFound() {
        when(userRepository.getByVerificationCode("code"))
                .thenReturn(Optional.empty());

        boolean result = userService.verifyUser("code");

        assertFalse(result);
    }
}
