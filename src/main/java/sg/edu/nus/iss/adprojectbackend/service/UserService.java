package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.User;

import java.time.LocalDateTime;

public interface UserService {
    Flux<UserDTO> getUsers();

    Mono<User> createUser(User user);

    Mono<UserDTO> findById(String id);

    Mono<Boolean> checkAllEmailExist(String email);

    Mono<User> findByEmail(String email);

    Mono<UserDTO> save(Mono<UserDTO> userDTO);

    Flux<UserDTO> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
