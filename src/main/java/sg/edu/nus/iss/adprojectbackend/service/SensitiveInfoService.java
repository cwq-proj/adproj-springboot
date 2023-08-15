package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.SensitiveInfoDTO;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;

public interface SensitiveInfoService {
    Flux<SensitiveInfoDTO> getSensitiveInfoList();

    Mono<SensitiveInfoDTO> findByNricAndFirstNameAndLastName(String nric, String firstName, String lastName);

    Mono<SensitiveInfoDTO> save(Mono<SensitiveInfoDTO> sensitiveInfoDTO);

    Mono<SensitiveInfoDTO> findById(String id);

    public Mono<SensitiveInfoDTO> getSensitiveInfoByNric(String nric);

    public Mono<SensitiveInfoDTO> getSensitiveInfoById(String id);

    public Mono<SensitiveInfo> updateSensitiveInfo(SensitiveInfoDTO sensitiveInfoDTO);
    
    public Mono<Boolean> checkNricExist(String nric);
}
