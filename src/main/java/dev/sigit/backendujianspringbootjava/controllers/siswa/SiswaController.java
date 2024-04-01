package dev.sigit.backendujianspringbootjava.controllers.siswa;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/siswa")
@RequiredArgsConstructor
public class SiswaController {

    @GetMapping("")
    public String helloSiswa(){
        return "Halo Siswa";
    }
}
