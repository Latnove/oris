package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserDto;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.repository.UserRepositoryHibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow();

    }

    @Transactional
    public UserDto save(UserDto dto) {
        return toDto(userRepository.save(toEntity(dto)));
    }

    @Transactional
    public UserDto updateUsername(UserDto dto) {

        User user = userRepository.findById(dto.getId())
                .orElseThrow();

        user.setUsername(dto.getUsername());

        return toDto(user);
    }

    @Transactional
    public UserDto deleteByUsername(String username) {
        return toDto(userRepository.deleteByUsername(username));
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        dto.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList()
        );
        return dto;
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        List<Role> roles = roleRepository.findAllByNameIn(dto.getRoles());
        user.setRoles(roles);
        return user;
    }

}
