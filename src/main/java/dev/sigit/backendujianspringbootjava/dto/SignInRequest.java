package dev.sigit.backendujianspringbootjava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {

    @NotBlank(message = "Email Atau ID Peserta Kosong!")
    @NotNull(message = "Email Atau ID Peserta Kosong!")
    private String emailOrIdPes;

    @NotBlank(message = "Password Kosong!")
    @NotNull(message = "Password Kosong!")
    private String password;
}
