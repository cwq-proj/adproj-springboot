package sg.edu.nus.iss.adprojectbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.service.HealthRecordService;
import sg.edu.nus.iss.adprojectbackend.service.HealthRecordServiceImpl;
import sg.edu.nus.iss.adprojectbackend.service.UserService;
import sg.edu.nus.iss.adprojectbackend.service.UserServiceImpl;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    HealthRecordServiceImpl healthRecordServiceImpl;

    @Autowired
    UserServiceImpl userServiceImpl;


    @PostMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/hello2")
    @PreAuthorize("hasRole('CLINIC')")
    public ResponseEntity<String> hello2(){
        return ResponseEntity.ok("hello2");
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Flux<UserDTO> getUsers() {
        return userServiceImpl.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/health-records")
    public Flux<HealthRecordDTO> getHealthRecords() {
        return healthRecordServiceImpl.getHealthRecords();
    }
}
