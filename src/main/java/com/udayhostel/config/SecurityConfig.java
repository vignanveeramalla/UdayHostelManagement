package com.udayhostel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpMethod;

import com.udayhostel.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig
{
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception
    {
        http

            // Disable CSRF because this is a REST API
            .csrf(csrf -> csrf.disable())

            // Enable CORS
            .cors(Customizer.withDefaults())

            // JWT authentication is stateless
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            // Authorization rules
            .authorizeHttpRequests(auth -> auth

                // =====================================
                // PUBLIC LOGIN
                // =====================================

                .requestMatchers(
                    "/auth/login"
                ).permitAll()


                // =====================================
                // PUBLIC COMPLAINT SUBMISSION
                // =====================================

                .requestMatchers(
                    HttpMethod.POST,
                    "/complaints"
                ).permitAll()


                // =====================================
                // PUBLIC ENQUIRY SUBMISSION
                // =====================================

                .requestMatchers(
                    HttpMethod.POST,
                    "/enquiries"
                ).permitAll()


                // =====================================
                // PUBLIC FACILITIES
                // =====================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/facilities"
                ).permitAll()


                // =====================================
                // PUBLIC ROOMS
                // =====================================

                .requestMatchers(
                    HttpMethod.GET,
                    "/rooms"
                ).permitAll()

                // =====================================
                // ADMIN APIs
                // =====================================

                .requestMatchers(
                    "/admins/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/students/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/rooms/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/payments/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/complaints/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/enquiries/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/facilities/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/dashboard/**"
                ).hasRole("ADMIN")


                // =====================================
                // EVERYTHING ELSE
                // =====================================

                .anyRequest().authenticated()
            )


            // =========================================
            // UNAUTHORIZED RESPONSE
            // =========================================

            .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                    new HttpStatusEntryPoint(
                        HttpStatus.UNAUTHORIZED
                    )
                )
            )


            // =========================================
            // JWT FILTER
            // =========================================

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );


        return http.build();
    }


    // =========================================
    // PASSWORD ENCODER
    // =========================================

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}