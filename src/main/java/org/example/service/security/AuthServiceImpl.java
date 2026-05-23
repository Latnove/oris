package org.example.service.security;

import io.jsonwebtoken.Claims;
import org.example.dto.security.JwtRefreshRequest;
import org.example.dto.security.JwtRequest;
import org.example.dto.security.JwtResponse;
import org.example.filter.JwtProvider;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Map<String, String> refreshStorage = new ConcurrentHashMap<>();
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        User user = userRepository.findByUsername(jwtRequest.login()).orElseThrow(() -> new UsernameNotFoundException(jwtRequest.login()));

        if (passwordEncoder.matches(jwtRequest.password(), user.getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(user);
            String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(jwtRequest.login(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        }

        throw new BadCredentialsException(jwtRequest.login());
    }

    @Override
    public JwtResponse refreshToken(JwtRefreshRequest jwtRefreshRequest) {
        if (jwtProvider.validateRefreshToken(jwtRefreshRequest.refreshToken())) {
            Claims claims = jwtProvider.getRefreshClaims(jwtRefreshRequest.refreshToken());
            String username = claims.getSubject();
            String refreshToken = refreshStorage.get(username);

            if (refreshToken != null && refreshToken.equals(jwtRefreshRequest.refreshToken())) {
                User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

                String accessToken = jwtProvider.generateAccessToken(user);
                String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshStorage.put(username, newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public JwtResponse token(JwtRefreshRequest jwtRefreshRequest) {
        return null;
    }
}
