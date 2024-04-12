package dev.sigit.backendujianspringbootjava.database.seeder;

import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import dev.sigit.backendujianspringbootjava.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SiswaSeeder {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createSiswa(){
        Pengguna pengguna = penggunaRepository.findFirstByRolePengguna(RolePengguna.SISWA);
        if (pengguna == null){
            Pengguna siswaBaru = new Pengguna();
            siswaBaru.setNama("Siswa Fake");
            siswaBaru.setEmail("siswa@fake.com");
            siswaBaru.setIdPeserta("SAJ-2003-33399");
            siswaBaru.setPassword(passwordEncoder.encode("qwertyu"));
            siswaBaru.setPasswordPlain("qwertyu");
            siswaBaru.setRolePengguna(RolePengguna.SISWA);
            siswaBaru.setCreatedAt(new Date());
            siswaBaru.setUpdatedAt(new Date());
            penggunaRepository.save(siswaBaru);
            System.out.println("Membuat Data Siswa Baru ✅");
        }
    }

}
