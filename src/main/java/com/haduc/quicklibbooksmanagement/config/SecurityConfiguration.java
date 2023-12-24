package com.haduc.quicklibbooksmanagement.config;

import com.haduc.quicklibbooksmanagement.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity

public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/book/**").permitAll()
                                .requestMatchers("/api/v1/author/**").permitAll()
                                .requestMatchers("/api/v1/api/v1/author-book/**").permitAll()
                                .requestMatchers("/api/v1/borrow-book-instance/**").hasAnyAuthority(UserRole.USER.name(), UserRole.LIBRARIAN.name())
                                .requestMatchers("/api/v1/borrow-request/**").hasAnyAuthority(UserRole.USER.name(), UserRole.LIBRARIAN.name())
                                .requestMatchers("/api/v1/library/**").permitAll()
                                .requestMatchers("/api/v1/library-book/**").permitAll()
                                .requestMatchers("/api/v1/user/**").hasAnyAuthority(UserRole.LIBRARIAN.name())
                                .requestMatchers("/api/v1/category/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
