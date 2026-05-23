package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/webjars/**", "/resources/**").permitAll()
                                .requestMatchers("/metrics").permitAll()
                                .requestMatchers("/benchmark").permitAll()
                                .requestMatchers("/hello").permitAll()
                                .requestMatchers("/users").permitAll()
                                .requestMatchers("/auth").permitAll()
                                .requestMatchers("/notes/public").permitAll()
                                .requestMatchers("/chat/public").permitAll()
                                .requestMatchers("/verification/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/chat/**", "/ws/**").hasRole("USER")
                                .requestMatchers("/notes/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
            ).formLogin(formLogin -> formLogin.defaultSuccessUrl("/chat", false));

        return http.build();
    }
}
