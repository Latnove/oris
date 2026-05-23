package org.example.service.security;

import org.example.dto.security.JwtRefreshRequest;
import org.example.dto.security.JwtRequest;
import org.example.dto.security.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest jwtRequest);
    JwtResponse refreshToken(JwtRefreshRequest jwtRefreshRequest);
    JwtResponse token(JwtRefreshRequest jwtRefreshRequest);
}
