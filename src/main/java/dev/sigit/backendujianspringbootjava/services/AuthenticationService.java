package dev.sigit.backendujianspringbootjava.services;

import dev.sigit.backendujianspringbootjava.dto.SignInRequest;
import dev.sigit.backendujianspringbootjava.dto.SignInResponse;
import dev.sigit.backendujianspringbootjava.dto.SignUpSiswaRequest;
import dev.sigit.backendujianspringbootjava.entities.Pengguna;

import java.util.concurrent.ExecutionException;

public interface AuthenticationService {
    SignInResponse signIn(SignInRequest signInRequest);
}
