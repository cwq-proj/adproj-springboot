package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;

public interface HealthRecordService {

    Flux<HealthRecordDTO> getHealthRecords();
    Flux<HealthRecordDTO> findHealthRecordsBySensitiveInfo(String sensitiveInfoId);
    
}
