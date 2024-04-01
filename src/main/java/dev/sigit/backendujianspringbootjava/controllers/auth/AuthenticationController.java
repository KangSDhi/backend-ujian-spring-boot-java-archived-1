package dev.sigit.backendujianspringbootjava.controllers.auth;

import dev.sigit.backendujianspringbootjava.dto.SignInRequest;
import dev.sigit.backendujianspringbootjava.dto.SignInResponse;
import dev.sigit.backendujianspringbootjava.dto.SignUpSiswaRequest;
import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signin(@Valid @RequestBody SignInRequest signInRequest) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
