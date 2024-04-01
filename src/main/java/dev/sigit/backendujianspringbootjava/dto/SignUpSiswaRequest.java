package dev.sigit.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class SignUpSiswaRequest {

    private String idPeserta;

    private String nama;

    private String email;

    private String password;
}
