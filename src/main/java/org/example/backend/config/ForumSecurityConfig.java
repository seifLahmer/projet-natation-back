package org.example.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class ForumSecurityConfig {

    // 1. CORS Configuration (forum-specific)
    @Bean
    public CorsFilter forumCorsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Angular
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/forum/**", config); // Forum-specific paths
        return new CorsFilter(source);
    }

    // 2. Security Filter Chain
    @Bean
    SecurityFilterChain forumSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/forum/**") // Only apply to forum paths
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/forum/posts/**").permitAll() // Public read access
                        .requestMatchers("/api/forum/comments").authenticated()
                        .requestMatchers("/api/forum/admin/**").hasRole("ADMIN")
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
