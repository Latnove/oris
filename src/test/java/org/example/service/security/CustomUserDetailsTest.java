package org.example.service.security;

import org.example.model.Role;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    @Test
    void getAuthorities_shouldReturnRolesWithPrefix() {
        Role role = new Role();
        role.setName("ADMIN");

        User user = new User();
        user.setRoles(List.of(role));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        List<? extends GrantedAuthority> authorities =
                (List<? extends GrantedAuthority>) userDetails.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.get(0).getAuthority());
    }

    @Test
    void getUsername_shouldReturnUsername() {
        User user = new User();
        user.setUsername("testUser");

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals("testUser", userDetails.getUsername());
    }

    @Test
    void getPassword_shouldReturnPassword() {
        User user = new User();
        user.setPassword("secret");

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertEquals("secret", userDetails.getPassword());
    }

    @Test
    void isEnabled_shouldBeFalseByDefault() {
        User user = new User();

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertFalse(userDetails.isEnabled());
    }

    @Test
    void isEnabled_shouldBeTrue_whenSetViaConstructor() {
        User user = new User();

        CustomUserDetails userDetails =
                new CustomUserDetails(user, true);

        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getAuthorities_shouldReturnEmptyList_whenNoRoles() {
        User user = new User();
        user.setRoles(List.of());

        CustomUserDetails userDetails = new CustomUserDetails(user);

        assertTrue(userDetails.getAuthorities().isEmpty());
    }
}