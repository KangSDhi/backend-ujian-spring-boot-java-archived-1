package dev.sigit.backendujianspringbootjava.repository;

import dev.sigit.backendujianspringbootjava.entities.Jurusan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JurusanRepository extends JpaRepository<Jurusan, Long> {
    Jurusan findJurusanByNama(String namaJurusan);
}
