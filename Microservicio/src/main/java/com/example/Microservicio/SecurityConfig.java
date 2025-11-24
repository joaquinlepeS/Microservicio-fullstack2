package com.example.Microservicio;

import com.example.Microservicio.security.JwtAuthenticationFilter;
import com.example.Microservicio.security.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                          CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                /* 🔓 Acceso público */
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/usuarios/registrar").permitAll()

                /* 🔥 RUTA CORRECTA DEL CATÁLOGO */
                .requestMatchers("/api/v1/productos/categoria/**").permitAll()

                /* Imágenes o recursos estáticos si los usas */
                .requestMatchers("/api/v1/public/**").permitAll()
                .requestMatchers("/api/v1/imagenes/**").permitAll()

                /* 🔓 Todos los productos GET son públicos */
                .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()

                /* 🔒 Administración de productos solo admin */
                .requestMatchers("/api/v1/productos/**").hasAuthority("ROLE_ADMIN")

                /* 🔒 Área admin */
                .requestMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")

                /* 🔒 Área cliente */
                .requestMatchers("/api/v1/cliente/**").hasAuthority("ROLE_CLIENTE")

                /* 🔓 Todo lo demás permitido */
                .anyRequest().permitAll()
            )

            /* Soporte h2-console */
            .headers(headers -> headers.frameOptions().disable())

            /* CORS permitir todo (React) */
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("*"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(false);
                return config;
            }))

            /* JWT antes del filtro de login */
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
