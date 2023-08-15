package sg.edu.nus.iss.adprojectbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.repository.UserRepository;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<UserDTO> getUsers() {
        Flux<User> userList = userRepository.findAll();
        return userList.map( (user) -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Mono<UserDTO> findById(String id) {
        Mono<User> user =  userRepository.findById(id);
        return user.map( (x) -> modelMapper.map(x, UserDTO.class));
    }

    @Override
    public Mono<UserDTO> save(Mono<UserDTO> userDTOMono) {
        return userDTOMono
                .map(userDTO -> modelMapper.map(userDTO, User.class))
                .flatMap(user -> userRepository.save(user))
                .map(savedUser -> modelMapper.map(savedUser, UserDTO.class));
    }

    @Override
    public Flux<UserDTO> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return userRepository.findAllByCreatedDateBetween(startDate, endDate)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Mono<Boolean> checkEmailExist(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public Mono<User> createUserAcc(UserDTO userDTO) {
        Mono<User> user = Mono.just(User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .username(userDTO.getFirstName() + userDTO.getLastName())
                .createdDate(LocalDateTime.now())
                .build());

        return user.flatMap(userRepository::save);
    }

    @Override
    public Mono<UserDTO> findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Mono<User> updateUserAcc(UserDTO userDTO, String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .flatMap(user -> {
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setPassword(userDTO.getPassword());
                    user.setUsername(userDTO.getFirstName() + userDTO.getLastName());

                    return userRepository.save(user);
                });
    }

}
