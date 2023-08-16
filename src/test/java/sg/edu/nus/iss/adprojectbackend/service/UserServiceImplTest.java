package sg.edu.nus.iss.adprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp(){
        userDTO = UserDTO.builder()
                .id("12345")
                .firstName("firstName")
                .lastName("lastName")
                .email("test@gmail.com")
                .password("123456")
                .createdDate(LocalDateTime.now())
                .build();

        user = modelMapper.map(userDTO, User.class);
    }

    @Test
    void mapUserDTOToUser() {
        assertTrue(user instanceof User, "user is not an instance of User");
    }

    @Test
    void saveUser() {
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<UserDTO> savedUserDTO = userService.save(Mono.just(userDTO));

        savedUserDTO.subscribe(savedUser -> {
            assertEquals(userDTO.getId(), savedUser.getId());
            assertEquals(userDTO.getFirstName(), savedUser.getFirstName());
        });
    }
}

