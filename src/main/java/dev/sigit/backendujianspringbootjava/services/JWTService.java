package dev.sigit.backendujianspringbootjava.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface JWTService {

    String extractUserName(String token);

    CompletableFuture<String> generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
