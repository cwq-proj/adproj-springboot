package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.CreateHealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;

public interface HealthRecordService {

    Flux<HealthRecordDTO> getHealthRecords();

    Flux<HealthRecordDTO> findHealthRecordsBySensitiveInfo(String sensitiveInfoId);

    Flux<HealthRecord> getSortedHealthRecordsByNric(String nric);

    Mono<HealthRecord> updateHealthRecord(String healthRecordId, HealthRecord updatedHealthRecord);

    Mono<Void> deleteHealthRecord(String healthRecordId);

    Mono<HealthRecord> getHealthRecord(String healthRecordId);

    public Mono<HealthRecordDTO> getHealthRecordById(String id);

    public Mono<HealthRecord> updateHealthRecord(HealthRecordDTO healthRecordDTO);
    
    public Mono<HealthRecord> createHealthRecord(CreateHealthRecordDTO createHealthRecordDTO);

}
