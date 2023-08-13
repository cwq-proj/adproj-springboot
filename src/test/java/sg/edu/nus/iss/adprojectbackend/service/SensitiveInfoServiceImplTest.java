package sg.edu.nus.iss.adprojectbackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import sg.edu.nus.iss.adprojectbackend.dto.SensitiveInfoDTO;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;
import sg.edu.nus.iss.adprojectbackend.repository.SensitiveInfoRepository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensitiveInfoServiceImplTest {

    @Mock
    private SensitiveInfoRepository sensitiveInfoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SensitiveInfoServiceImpl sensitiveInfoService;

    @Test
    public void testGetSensitiveInfoByNric() {
        SensitiveInfo mockInfo = new SensitiveInfo();
        mockInfo.setNric("S1234567A");

        // Mock the behavior of the repository
        when(sensitiveInfoRepository.findByNric(anyString())).thenReturn(Mono.just(mockInfo));

        // Call the method to be tested
        Mono<SensitiveInfoDTO> resultMono = sensitiveInfoService.getSensitiveInfoByNric("S1234567A");

        // Use StepVerifier to assert and verify the behavior of the Mono
        StepVerifier.create(resultMono)
                .expectNextMatches(dto -> dto.getNric().equals("S1234567A"))
                .verifyComplete();
    }
}
