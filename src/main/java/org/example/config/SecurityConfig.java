package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and().build();
//
//    }

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
                                .requestMatchers("/auth").permitAll()
                                .requestMatchers("/notes/public").permitAll()
                                .requestMatchers("/verification/**").permitAll()
                                .requestMatchers("/notes/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/admin/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/hello").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
            ).formLogin(Customizer.withDefaults());

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web ->  web.ignoring()
//                .requestMatchers("/css/**", "/template/**", "/assets/**", "/js/**");
//    }
}