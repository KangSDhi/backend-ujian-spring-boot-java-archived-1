package dev.sigit.backendujianspringbootjava.controllers.guru;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/guru")
@RequiredArgsConstructor
public class GuruController {

    @GetMapping("")
    public String helloGuru(){
        return "Halo Guru";
    }
}
