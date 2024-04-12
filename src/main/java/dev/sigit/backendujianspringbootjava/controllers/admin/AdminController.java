package dev.sigit.backendujianspringbootjava.controllers.admin;

import dev.sigit.backendujianspringbootjava.dto.CreatePenggunaResponse;
import dev.sigit.backendujianspringbootjava.dto.CreateSiswaRequest;
import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.services.SiswaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SiswaService siswaService;

    @GetMapping("")
    public String helloAdmin(){
        return "Halo Admin";
    }

    @PostMapping("/siswa/create")
    public ResponseEntity<CreatePenggunaResponse<HashMap<String, String>>> createSiswa(@RequestBody CreateSiswaRequest createSiswaRequest){
        return ResponseEntity.ok(siswaService.createSiswa(createSiswaRequest));
    }
}
