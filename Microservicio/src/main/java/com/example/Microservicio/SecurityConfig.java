package com.example.Microservicio;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // PERMITIR H2 CONSOLE
                .requestMatchers("/h2-console/**").permitAll()

                // Endpoints del front
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/usuarios/**").permitAll()
                .requestMatchers("/api/v1/productos/**").permitAll()
                .requestMatchers("/api/v1/carrito/**").permitAll()

                .anyRequest().permitAll()
            )
            // NECESARIO PARA VER H2
            .headers(headers -> headers.frameOptions().disable())

            // CORS
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("*"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            }));

        return http.build();
    }
}
