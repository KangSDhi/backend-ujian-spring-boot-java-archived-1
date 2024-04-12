package dev.sigit.backendujianspringbootjava.database.seeder;

import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import dev.sigit.backendujianspringbootjava.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminSeeder {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createAdmin(){
        Pengguna pengguna = penggunaRepository.findFirstByRolePengguna(RolePengguna.ADMIN);
        if (pengguna == null){
            Pengguna adminBaru = new Pengguna();
            adminBaru.setNama("Kang Admin");
            adminBaru.setEmail("kangadmin@gmail.com");
            adminBaru.setIdPeserta(null);
            adminBaru.setPassword(passwordEncoder.encode("qwerty"));
            adminBaru.setRolePengguna(RolePengguna.ADMIN);
            adminBaru.setCreatedAt(new Date());
            adminBaru.setUpdatedAt(new Date());
            penggunaRepository.save(adminBaru);
            System.out.println("Membuat Data Admin Baru ✅");
        }
    }
}
