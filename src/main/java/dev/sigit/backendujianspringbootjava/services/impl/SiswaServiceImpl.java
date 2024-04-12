package dev.sigit.backendujianspringbootjava.services.impl;

import dev.sigit.backendujianspringbootjava.dto.CreatePenggunaResponse;
import dev.sigit.backendujianspringbootjava.dto.CreateSiswaRequest;
import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import dev.sigit.backendujianspringbootjava.repository.PenggunaRepository;
import dev.sigit.backendujianspringbootjava.services.SiswaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class SiswaServiceImpl implements SiswaService {

    private final PenggunaRepository penggunaRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public CreatePenggunaResponse<HashMap<String, String>> createSiswa(CreateSiswaRequest createSiswaRequest) {

        Pengguna siswa = new Pengguna();

        siswa.setNama(createSiswaRequest.getNama());
        siswa.setIdPeserta(createSiswaRequest.getIdPeserta());
        siswa.setEmail(createSiswaRequest.getEmail());
        siswa.setPassword(passwordEncoder.encode(createSiswaRequest.getPassword()));
        siswa.setPasswordPlain(createSiswaRequest.getPassword());
        siswa.setRolePengguna(RolePengguna.SISWA);
        siswa.setCreatedAt(new Date());
        siswa.setUpdatedAt(new Date());

        Pengguna siswaBaru = penggunaRepository.save(siswa);

        HashMap<String, String> data = new HashMap<>();
        data.put("idPeserta", siswaBaru.getIdPeserta());
        data.put("nama", siswaBaru.getNama());
        data.put("email", siswaBaru.getEmail());
        data.put("role", siswaBaru.getRolePengguna().name());

        CreatePenggunaResponse<HashMap<String, String>> createPenggunaResponse = new CreatePenggunaResponse<>();
        createPenggunaResponse.setMessage("Berhasil Membuat Siswa Baru!");
        createPenggunaResponse.setData(data);

        return createPenggunaResponse;
    }
}
