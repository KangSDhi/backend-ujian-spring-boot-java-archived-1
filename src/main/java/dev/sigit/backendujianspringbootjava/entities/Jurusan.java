package dev.sigit.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jurusan")
public class Jurusan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_jurusan", unique = true)
    private String nama;
}
