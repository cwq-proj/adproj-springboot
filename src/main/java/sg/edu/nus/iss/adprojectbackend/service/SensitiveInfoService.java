package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.SensitiveInfoDTO;

public interface SensitiveInfoService {
    Flux<SensitiveInfoDTO> getSensitiveInfoList();

    Mono<SensitiveInfoDTO> findByNricAndFirstNameAndLastName(String nric, String firstName, String lastName);

    Mono<SensitiveInfoDTO> save(Mono<SensitiveInfoDTO> sensitiveInfoDTO);

    Mono<SensitiveInfoDTO> findById(String id);
}
