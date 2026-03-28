package org.example.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.config.properties.MailProperties;
import org.example.dto.UserDto;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;


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
    public void createUser(UserDto userDto) {
        User user = toEntity(userDto);
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);

        sendVerificationMail(user);
    }

    @Transactional
    public boolean verifyUser(String verificationCode) {
        Optional<User> userOpt = userRepository.getByVerificationCode(verificationCode);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        user.setEnabled(true);

        userRepository.save(user);
        return true;
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

    private void sendVerificationMail(User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        String content = mailProperties.getContent();
        try {
            mimeMessageHelper.setFrom(mailProperties.getFrom(), mailProperties.getSender());
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setSubject(mailProperties.getSubject());

            content = content.replace("$name", user.getUsername());
            content = content.replace("$url",  mailProperties.getBaseUrl() + "/verification?code=" + user.getVerificationCode());

            mimeMessageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        dto.setEmail(user.getEmail());
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
        user.setEmail(dto.getEmail());
        List<Role> roles = roleRepository.findAllByNameIn(dto.getRoles());
        user.setRoles(roles);
        return user;
    }

}
