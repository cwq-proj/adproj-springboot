/*
package sg.edu.nus.iss.adprojectbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.service.SensitiveInfoService;
import sg.edu.nus.iss.adprojectbackend.service.StaffService;
import sg.edu.nus.iss.adprojectbackend.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    UserService userServiceImpl;

    @Autowired
    StaffService staffServiceImpl;

    @Autowired
    SensitiveInfoService sensitiveInfoServiceImpl;

    @GetMapping("/checkEmail/{email}")
    public Mono<Boolean> checkEmailExist(@PathVariable String email) {
        Mono<Boolean> userEmailAvail = userServiceImpl.checkEmailExist(email);
        Mono<Boolean> staffEmailAvail = staffServiceImpl.checkEmailExist(email);

        return userEmailAvail.flatMap(x -> {
            if (x) {
                return Mono.just(true);
            } else {
                return staffEmailAvail;
            }
        });
    }

    @PostMapping("/create")
    @Transactional
    public Mono<User> createUserAccount(@RequestBody UserDTO userDTO) {
        return userServiceImpl.createUserAcc(userDTO);
    }
}
*/
