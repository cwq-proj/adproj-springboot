package sg.edu.nus.iss.adprojectbackend.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.dto.LinkHealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.dto.SensitiveInfoDTO;
import sg.edu.nus.iss.adprojectbackend.dto.UserDTO;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;
import sg.edu.nus.iss.adprojectbackend.service.HealthRecordServiceImpl;
import sg.edu.nus.iss.adprojectbackend.service.SensitiveInfoServiceImpl;
import sg.edu.nus.iss.adprojectbackend.service.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HealthRecordControllerTest {

    @InjectMocks
    private HealthRecordController healthRecordController;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private SensitiveInfoServiceImpl sensitiveInfoServiceImpl;

    @Mock
    private HealthRecordServiceImpl healthRecordServiceImpl;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    void linkHealthRecordToSensitiveInfo() {
        String userId = "user123";
        String sensitiveInfoId = "sensitive123";

        // Prepare mock data
        LinkHealthRecordDTO recordDTO = new LinkHealthRecordDTO();
        recordDTO.setUserId(userId);

        SensitiveInfo sensitiveInfo = new SensitiveInfo();
        sensitiveInfo.setId(sensitiveInfoId);

        when(sensitiveInfoServiceImpl.findByNricAndFirstNameAndLastName(any(), any(), any())).
                thenReturn(Mono.just(modelMapper.map(sensitiveInfo, SensitiveInfoDTO.class)));
        when(userServiceImpl.findById(userId)).thenReturn(Mono.just(new UserDTO()));
        when(userServiceImpl.save(any(Mono.class))).thenReturn(Mono.empty());
        when(sensitiveInfoServiceImpl.findById(sensitiveInfoId)).
                thenReturn(Mono.just(modelMapper.map(sensitiveInfo, SensitiveInfoDTO.class)));
        when(sensitiveInfoServiceImpl.save(any(Mono.class))).thenReturn(Mono.empty());
        when(healthRecordServiceImpl.findHealthRecordsBySensitiveInfo(sensitiveInfoId)).thenReturn(Flux.empty()); // Provide your expected result here


        WebTestClient.bindToController(healthRecordController)
                .build()
                .post()
                .uri("/health-records/link")
                .bodyValue(recordDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HealthRecordDTO.class)
                .hasSize(0); // Verify the response size
    }
}