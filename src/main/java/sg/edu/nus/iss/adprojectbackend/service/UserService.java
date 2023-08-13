package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;

import java.time.LocalDateTime;

public interface UserService {
    Flux<UserDTO> getUsers();

    Mono<UserDTO> findById(String id);

    Mono<UserDTO> save(Mono<UserDTO> userDTO);

    Flux<UserDTO> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
