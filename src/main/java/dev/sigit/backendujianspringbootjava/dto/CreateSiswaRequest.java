package dev.sigit.backendujianspringbootjava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSiswaRequest {

    @NotBlank(message = "ID Peserta Kosong!")
    @NotNull(message = "ID Peserta Kosong!")
    private String idPeserta;

    @NotBlank(message = "Nama Kosong!")
    @NotNull(message = "Nama Kosong!")
    private String nama;

    @NotBlank(message = "Email Kosong!")
    @NotNull(message = "Email Kosong!")
    private String email;

    @NotBlank(message = "Password Kosong!")
    @NotNull(message = "Password Kosong!")
    private String password;

}
