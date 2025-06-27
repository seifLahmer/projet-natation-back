package tn.esprit.natationproject.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import tn.esprit.natationproject.repositories.UtilisateurRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UtilisateurRepository utilisateurRepository;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final tn.esprit.natationproject.services.CustomUserDetailsService userDetailsService;
    private final CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(corsFilter, BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(
                            "/api/auth/**",
                            "/api/inscriptions/**",
                            "/api/documents/**",
                            "/api/competitions/**",
                            "/api/resultats/**",
                            "/api/licences/parmail/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/licences/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/licences/{id}").permitAll()
                        .requestMatchers("/error").permitAll()  // Allow error endpoints
                        .requestMatchers("/api/admin/**").hasAnyAuthority("ADMIN", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/licences/**").hasAnyAuthority("CHEF_EQUIPE", "ROLE_CHEF_EQUIPE")
                        .requestMatchers(HttpMethod.DELETE, "/api/licences/**").hasAnyAuthority("CHEF_EQUIPE", "ROLE_CHEF_EQUIPE")
                        .requestMatchers(HttpMethod.GET, "/api/licences/**").authenticated()
                        .requestMatchers("/forum/**").authenticated()
                        .requestMatchers("/api/chef/**").hasAnyAuthority("CHEF_EQUIPE", "ROLE_CHEF_EQUIPE")
                        .requestMatchers("/api/joueurs/**").hasAnyAuthority("CHEF_EQUIPE", "ROLE_CHEF_EQUIPE")

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider());

        // Add JWT filter only for non-PUT /api/licences requests
        http.addFilterBefore(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {
                if (request.getMethod().equals("PUT") && request.getRequestURI().startsWith("/api/licences/")) {
                    filterChain.doFilter(request, response);
                } else {
                    jwtAuthFilter.doFilterInternal(request, response, filterChain);
                }
            }
        }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}