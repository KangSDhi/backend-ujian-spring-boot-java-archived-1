package dev.sigit.backendujianspringbootjava.services;

import dev.sigit.backendujianspringbootjava.dto.CreatePenggunaResponse;
import dev.sigit.backendujianspringbootjava.dto.CreateSiswaRequest;
import dev.sigit.backendujianspringbootjava.entities.Pengguna;

import java.util.HashMap;

public interface SiswaService {

    CreatePenggunaResponse<HashMap<String, String>> createSiswa(CreateSiswaRequest createSiswaRequest);
}
