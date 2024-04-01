package dev.sigit.backendujianspringbootjava.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sigit.backendujianspringbootjava.dto.ErrorResponse;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import dev.sigit.backendujianspringbootjava.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Objects;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers("/api/admin/**").hasAnyAuthority(RolePengguna.ADMIN.name())
                        .requestMatchers("/api/guru/**").hasAnyAuthority(RolePengguna.GURU.name())
                        .requestMatchers("/api/siswa/**").hasAnyAuthority(RolePengguna.SISWA.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            ErrorResponse<String> errorResponse = new ErrorResponse<>();
                            errorResponse.setHttpCode(HttpStatus.UNAUTHORIZED.value());
                            errorResponse.setErrors("Unauthorized");
                            ResponseEntity<ErrorResponse<String>> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(errorResponse);
                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write(jsonResponse);
                            response.getWriter().flush();
                            logger.error(authException.getMessage());
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            ErrorResponse<String> errorResponse = new ErrorResponse<>();
                            errorResponse.setHttpCode(HttpStatus.FORBIDDEN.value());
                            errorResponse.setErrors("Access Denied");
                            ResponseEntity<ErrorResponse<String>> responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN)
                                    .body(errorResponse);
                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write(jsonResponse);
                            response.getWriter().flush();
                            logger.error(accessDeniedException.getMessage());
                        }));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
