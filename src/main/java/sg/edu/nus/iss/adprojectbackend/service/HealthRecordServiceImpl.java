package sg.edu.nus.iss.adprojectbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;
import sg.edu.nus.iss.adprojectbackend.repository.HealthRecordRepository;
import sg.edu.nus.iss.adprojectbackend.repository.SensitiveInfoRepository;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final SensitiveInfoRepository sensitiveInfoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HealthRecordServiceImpl(HealthRecordRepository healthRecordRepository, SensitiveInfoRepository sensitiveInfoRepository,
                                   ModelMapper modelMapper) {
        this.healthRecordRepository = healthRecordRepository;
        this.sensitiveInfoRepository = sensitiveInfoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<HealthRecordDTO> getHealthRecords() {
        Flux<HealthRecord> healthRecordList = healthRecordRepository.findAll();
        return healthRecordList.map( (record) -> modelMapper.map(record, HealthRecordDTO.class));
    }

    @Override
    public Flux<HealthRecordDTO> findHealthRecordsBySensitiveInfo(String sensitiveInfoId) {
        Flux<HealthRecord> healthRecords = healthRecordRepository.findBySensitiveInfo(sensitiveInfoId);
        return healthRecords.map(record -> modelMapper.map(record, HealthRecordDTO.class));
    }

    @Override
    public Flux<HealthRecord> getSortedHealthRecordsByNric(String nric) {

        return sensitiveInfoRepository.findByNric(nric)
            .flatMapMany(userSensitiveInfo -> healthRecordRepository.findBySensitiveInfoOrderByCreatedDateDesc(userSensitiveInfo.getId()));
    }

    @Override
    public Mono<HealthRecord> updateHealthRecord(String healthRecordId, HealthRecord updatedHealthRecord) {
        
        Mono<HealthRecord> existingRecordMono = healthRecordRepository.findById(healthRecordId);

        return existingRecordMono.flatMap(existingRecord -> {
            existingRecord.setAge(updatedHealthRecord.getAge());
            existingRecord.setBMI(updatedHealthRecord.getBMI());
            existingRecord.setBPMeds(updatedHealthRecord.getBPMeds());
            existingRecord.setCigsPerDay(updatedHealthRecord.getCigsPerDay());
            existingRecord.setCurrentSmoker(updatedHealthRecord.getCurrentSmoker());
            existingRecord.setDiaBP(updatedHealthRecord.getDiaBP());
            existingRecord.setDiabetes(updatedHealthRecord.getDiabetes());
            existingRecord.setEducation(updatedHealthRecord.getEducation());
            existingRecord.setGlucose(updatedHealthRecord.getGlucose());
            existingRecord.setHeartRate(updatedHealthRecord.getHeartRate());
            existingRecord.setMale(updatedHealthRecord.getMale());
            existingRecord.setPrevalentHyp(updatedHealthRecord.getPrevalentHyp());
            existingRecord.setPrevalentStroke(updatedHealthRecord.getPrevalentStroke());
            existingRecord.setSysBP(updatedHealthRecord.getSysBP());
            existingRecord.setTotChol(updatedHealthRecord.getTotChol());
            return healthRecordRepository.save(existingRecord);
        });
    }

    @Override
    public Mono<Void> deleteHealthRecord(String healthRecordId) {
        return healthRecordRepository.deleteById(healthRecordId);
    }

    @Override
    public Mono<HealthRecord> getHealthRecord(String healthRecordId) {
        return healthRecordRepository.findById(healthRecordId);
    }

}
