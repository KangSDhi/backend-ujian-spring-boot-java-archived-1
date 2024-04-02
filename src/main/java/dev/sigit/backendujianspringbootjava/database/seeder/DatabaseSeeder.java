package dev.sigit.backendujianspringbootjava.database.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class DatabaseSeeder {

    @Autowired
    JurusanSeeder jurusanSeeder;

    @Autowired
    AdminSeeder adminSeeder;

    @Autowired
    GuruSeeder guruSeeder;

    @Autowired
    SiswaSeeder siswaSeeder;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {
        jurusanSeeder.createJurusan();
        adminSeeder.createAdmin();
        guruSeeder.createGuru();
        siswaSeeder.createSiswa();
    }
}
