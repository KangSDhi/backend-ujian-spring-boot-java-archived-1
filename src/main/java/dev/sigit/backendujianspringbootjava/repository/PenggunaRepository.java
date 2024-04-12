package dev.sigit.backendujianspringbootjava.repository;

import dev.sigit.backendujianspringbootjava.entities.Pengguna;
import dev.sigit.backendujianspringbootjava.entities.RolePengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, Long> {

    Optional<Pengguna> findByEmail(String email);

    Optional<Pengguna> findByNama(String nama);

    Optional<Pengguna> findByIdPeserta(String idPeserta);

    Pengguna findByRolePengguna(RolePengguna rolePengguna);

    Pengguna findFirstByRolePengguna(RolePengguna rolePengguna);
}
