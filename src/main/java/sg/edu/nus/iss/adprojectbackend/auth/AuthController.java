package sg.edu.nus.iss.adprojectbackend.auth;


import lombok.Data;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.model.Staff;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.service.StaffServiceImpl;
import sg.edu.nus.iss.adprojectbackend.service.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@RestController
@RequestMapping("/login")
public class AuthController {
    final JwtService jwtService;

    final PasswordEncoder passwordEncoder;

    final AuthManager authManager;

    final UserServiceImpl userServiceImpl;

    final StaffServiceImpl staffServiceImpl;


    @GetMapping("/checkEmail/{email}")
    public Mono<Boolean> checkEmailExist(@PathVariable String email) {
        Mono<Boolean> userEmailAvail = userServiceImpl.checkAllEmailExist(email);
        return userEmailAvail;
    }

    @GetMapping("/password")
    public String password(@RequestParam String pwd){
        return  passwordEncoder.encode(pwd);
    }

/*    @GetMapping("/auth")
    public Mono<ResponseEntity<RequestResponse<String>>> auth(){
        return Mono.just(ResponseEntity.ok(
                new RequestResponse<>("hello","success")
                )
        );
    }*/

    @GetMapping("/checkToken")
    public Mono<ResponseEntity<RequestResponse<String>>> checkToken(@RequestParam String token){
        Date expirationDate = jwtService.extractExpiration(token);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedExpiration = sdf.format(expirationDate);

        return Mono.just(ResponseEntity.ok(
                        new RequestResponse<>(formattedExpiration,token)
                )
        );
    }



    //User login
    @PostMapping("/login")
    public  Mono<ResponseEntity<RequestResponse<String>>> login(@RequestBody AuthenticationRequest request){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()
        ));
        Mono<User> foundUser = userServiceImpl.findByEmail(request.getEmail());

        return foundUser.flatMap(u ->{
                if (passwordEncoder.matches(request.getPassword(),u.getPassword())){
                    var jwtToken = jwtService.generateToken(u);
                    return Mono.just(
                            ResponseEntity.ok(
                                    new RequestResponse<String>(jwtToken,"success")
                            )
                    );
                }
                return Mono.just(
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(new RequestResponse<String>("","Invalid Credentials"))
                );
        }).switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new RequestResponse<>("","User not found"))));
/*        Mono<UserDetails> foundUser = user.findByUsername(request.getEmail());
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()
        ));
        return userServiceImpl.findByEmail(request.getEmail())
                .flatMap(user ->{
                    var jwtToken = jwtService.generateToken(user);
                    RequestResponse<String> response = RequestResponse.<String>builder()
                            .data(jwtToken)
                            .message("success")
                            .build();
                    return Mono.just(ResponseEntity.ok(response));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(RequestResponse.<String>builder()
                                .message("login failed")
                                .build()));*/
    }


    //User register
    @PostMapping("/register")
    public Mono<ResponseEntity<RequestResponse<String>>> register(@RequestBody RegisterRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Check if the email already exists
        Mono<Boolean> userEmailAvail = userServiceImpl.checkAllEmailExist(request.getEmail());

        return userEmailAvail.flatMap(emailExists -> {
            if (emailExists) {
                RequestResponse<String> errorResponse = RequestResponse.<String>builder()
                        .data("")
                        .message("Email already exists")
                        .build();
                return Mono.just(ResponseEntity.ok(errorResponse));
            } else {
                var user1 = User.builder()
                        .firstName(request.getFirstname())
                        .lastName(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .username(request.getUsername())
                        .createdDate(currentDateTime)
                        .role("USER")
                        .build();

                return userServiceImpl.createUser(user1)
                        .flatMap(user -> {
                            var jwtToken = jwtService.generateToken(user);
                            RequestResponse<String> successResponse = RequestResponse.<String>builder()
                                    .data(jwtToken)
                                    .message("Register success")
                                    .build();
                            return Mono.just(ResponseEntity.ok(successResponse));
                        });
            }
        });
    }

    @PostMapping("/Adminlogin")
    public  Mono<ResponseEntity<RequestResponse<String>>> Adminlogin(@RequestBody AuthenticationRequest request){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),request.getPassword()
        ));
        Mono<Staff> foundUser = staffServiceImpl.findByEmail(request.getEmail());

        return foundUser.flatMap(u ->{
            if (passwordEncoder.matches(request.getPassword(),u.getPassword())){
                var jwtToken = jwtService.generateToken(u);
                return Mono.just(
                        ResponseEntity.ok(
                                new RequestResponse<String>(jwtToken,"success")
                        )
                );
            }
            return Mono.just(
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new RequestResponse<String>("","Invalid Credentials"))
            );
        }).switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new RequestResponse<>("","Staff not found"))));
    }


    //Admin register
    @PostMapping("/Adminregister")
    public Mono<ResponseEntity<RequestResponse<String>>> Adminregister(@RequestBody RegisterRequest request) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Check if the email already exists
        Mono<Boolean> userEmailAvail = userServiceImpl.checkAllEmailExist(request.getEmail());

        return userEmailAvail.flatMap(emailExists -> {
            if (emailExists) {
                RequestResponse<String> errorResponse = RequestResponse.<String>builder()
                        .data("")
                        .message("Email already exists")
                        .build();
                return Mono.just(ResponseEntity.ok(errorResponse));
            } else {
                var user1 = User.builder()
                        .firstName(request.getFirstname())
                        .lastName(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .username(request.getUsername())
                        .createdDate(currentDateTime)
                        .role("USER")
                        .build();

                return userServiceImpl.createUser(user1)
                        .flatMap(user -> {
                            var jwtToken = jwtService.generateToken(user);
                            RequestResponse<String> successResponse = RequestResponse.<String>builder()
                                    .data(jwtToken)
                                    .message("Register success")
                                    .build();
                            return Mono.just(ResponseEntity.ok(successResponse));
                        });
            }
        });
    }

    @GetMapping("/logout")
    public Mono<ResponseEntity<RequestResponse<String>>> logout(@RequestParam String token) {
        JwtBlacklist.addToBlacklist(token);
        return Mono.just(ResponseEntity.ok(new RequestResponse<String>(token,"logout success")));
    }


}
