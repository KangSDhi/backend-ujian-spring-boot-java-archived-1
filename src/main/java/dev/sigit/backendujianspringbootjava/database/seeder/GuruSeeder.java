package dev.sigit.backendujianspringbootjava.database.seeder;

import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import dev.sigit.backendujianspringbootjava.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GuruSeeder {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createGuru(){
        Pengguna pengguna = penggunaRepository.findFirstByRolePengguna(RolePengguna.GURU);
        if (pengguna == null){
            Pengguna guruBaru = new Pengguna();
            guruBaru.setNama("Kang Guru");
            guruBaru.setEmail("kangguru@gmail.com");
            guruBaru.setIdPeserta(null);
            guruBaru.setPassword(passwordEncoder.encode("ytrewq"));
            guruBaru.setRolePengguna(RolePengguna.GURU);
            guruBaru.setCreatedAt(new Date());
            guruBaru.setUpdatedAt(new Date());
            penggunaRepository.save(guruBaru);
            System.out.println("Membuat Data Guru Baru ✅");
        }
    }
}
