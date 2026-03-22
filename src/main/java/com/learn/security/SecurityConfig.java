package com.learn.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/employees/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees/paginated").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/departments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/departments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/departments/*/employees").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/employees/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees/department/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees/salary/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees/search/*").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {});
        return http.build();
    }
}