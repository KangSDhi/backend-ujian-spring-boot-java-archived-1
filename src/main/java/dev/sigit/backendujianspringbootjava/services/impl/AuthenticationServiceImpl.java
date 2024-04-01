package dev.sigit.backendujianspringbootjava.services.impl;

import dev.sigit.backendujianspringbootjava.dto.SignInRequest;
import dev.sigit.backendujianspringbootjava.dto.SignInResponse;
import dev.sigit.backendujianspringbootjava.dto.SignUpSiswaRequest;
import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import dev.sigit.backendujianspringbootjava.repository.PenggunaRepository;
import dev.sigit.backendujianspringbootjava.services.AuthenticationService;
import dev.sigit.backendujianspringbootjava.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PenggunaRepository penggunaRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Override
    public Pengguna signUpSiswa(SignUpSiswaRequest signUpSiswaRequest) {
        Pengguna pengguna = new Pengguna();

        pengguna.setNama(signUpSiswaRequest.getNama());
        pengguna.setIdPeserta(signUpSiswaRequest.getIdPeserta());
        pengguna.setEmail(signUpSiswaRequest.getEmail());
        pengguna.setPassword(passwordEncoder.encode(signUpSiswaRequest.getPassword()));
        pengguna.setRolePengguna(RolePengguna.SISWA);

        return penggunaRepository.save(pengguna);
    }

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) throws ExecutionException, InterruptedException {

        new Pengguna();
        Pengguna user;

        if (validateEmail(signInRequest.getEmailOrIdPes())){
            user = penggunaRepository.findByEmail(signInRequest.getEmailOrIdPes()).orElseThrow(() -> new UsernameNotFoundException("Email atau Password Salah!"));
            if (user != null && passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmailOrIdPes(), signInRequest.getPassword()));
            } else {
                throw new UsernameNotFoundException("Email atau Password Salah");
            }
        }else {
            Pengguna pengguna = penggunaRepository.findByIdPeserta(signInRequest.getEmailOrIdPes()).orElseThrow(() -> new UsernameNotFoundException("ID Peserta atau Password Salah!"));
            if (pengguna != null && passwordEncoder.matches(signInRequest.getPassword(), pengguna.getPassword())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pengguna.getEmail(), signInRequest.getPassword()));
                user = pengguna;
            } else {
                throw new UsernameNotFoundException("ID Peserta atau Password Salah!");
            }
        }

        CompletableFuture<String> jwt = jwtService.generateToken(user);

        SignInResponse signInResponse = new SignInResponse();
        signInResponse.setToken(jwt.get());

        return signInResponse;
    }

    private boolean validateEmail(String email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
