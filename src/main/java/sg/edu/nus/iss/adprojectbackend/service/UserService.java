package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.User;

import java.time.LocalDateTime;

public interface UserService {
    Flux<UserDTO> getUsers();

    Mono<UserDTO> findById(String id);

    Mono<UserDTO> save(Mono<UserDTO> userDTO);

    Flux<UserDTO> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    public Mono<Boolean> checkEmailExist(String email);

    public Mono<User> createUserAcc(UserDTO userDTO);

    public Mono<UserDTO> findUserByEmail(String email);

    public Mono<User> updateUserAcc(UserDTO userDTO, String email);
}
