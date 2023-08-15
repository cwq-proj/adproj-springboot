package sg.edu.nus.iss.adprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clinic")
@RequiredArgsConstructor
public class ClinicController {
    @PostMapping("/hello")
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello2");
    }
}
