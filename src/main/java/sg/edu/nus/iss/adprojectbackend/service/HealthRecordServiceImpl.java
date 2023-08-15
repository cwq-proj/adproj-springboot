package sg.edu.nus.iss.adprojectbackend.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.CreateHealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;
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

    @Override
    public Mono<HealthRecordDTO> getHealthRecordById(String id) {
        return healthRecordRepository.findById(id).map(record -> modelMapper.map(record, HealthRecordDTO.class))
                .defaultIfEmpty(new HealthRecordDTO());
    }

    @Override
    public Mono<HealthRecord> updateHealthRecord(HealthRecordDTO healthRecordDTO) {
        return healthRecordRepository.findById(healthRecordDTO.getId())
                .flatMap(record -> {
                    record.setMale(healthRecordDTO.getMale());
                    record.setAge(healthRecordDTO.getAge());
                    record.setEducation(healthRecordDTO.getEducation());
                    record.setCurrentSmoker(healthRecordDTO.getCurrentSmoker());
                    record.setCigsPerDay(healthRecordDTO.getCigsPerDay());
                    record.setBPMeds(healthRecordDTO.getBPMeds());
                    record.setPrevalentStroke(healthRecordDTO.getPrevalentStroke());
                    record.setPrevalentHyp(healthRecordDTO.getPrevalentHyp());
                    record.setDiabetes(healthRecordDTO.getDiabetes());
                    record.setTotChol(healthRecordDTO.getTotChol());
                    record.setSysBP(healthRecordDTO.getSysBP());
                    record.setDiaBP(healthRecordDTO.getDiaBP());
                    record.setBMI(healthRecordDTO.getBMI());
                    record.setHeartRate(healthRecordDTO.getHeartRate());
                    record.setGlucose(healthRecordDTO.getGlucose());
                    record.setTenYearCHD(healthRecordDTO.getTenYearCHD());
                    return healthRecordRepository.save(record);
                });
    }

    @Override
    public Mono<HealthRecord> createHealthRecord(CreateHealthRecordDTO createHealthRecordDTO) {
        return sensitiveInfoRepository.findByNricIgnoreCase(createHealthRecordDTO.getNric())
            .flatMap(sensitiveInfo -> {
                return saveHealthRecord(sensitiveInfo.getId(), createHealthRecordDTO);
            })
            .switchIfEmpty(Mono.defer(() -> {
                SensitiveInfo newSensitiveInfo = new SensitiveInfo();
                newSensitiveInfo.setFirstName(createHealthRecordDTO.getFirstName());
                newSensitiveInfo.setLastName(createHealthRecordDTO.getLastName());
                newSensitiveInfo.setNric(createHealthRecordDTO.getNric());
                newSensitiveInfo.setPhoneNumber(createHealthRecordDTO.getPhoneNumber());
                newSensitiveInfo.setCreatedDate(LocalDateTime.now());

                return sensitiveInfoRepository.save(newSensitiveInfo)
                        .flatMap(info -> saveHealthRecord(info.getId(), createHealthRecordDTO));
            }));
    }

    private Mono<HealthRecord> saveHealthRecord(String sensitiveInfoId, CreateHealthRecordDTO createHealthRecordDTO) {
        HealthRecord healthRecord = new HealthRecord();

        healthRecord.setMale(createHealthRecordDTO.getMale());
        healthRecord.setAge(createHealthRecordDTO.getAge());
        healthRecord.setEducation(createHealthRecordDTO.getEducation());
        healthRecord.setCurrentSmoker(createHealthRecordDTO.getCurrentSmoker());
        healthRecord.setCigsPerDay(createHealthRecordDTO.getCigsPerDay());
        healthRecord.setBPMeds(createHealthRecordDTO.getBPMeds());
        healthRecord.setPrevalentStroke(createHealthRecordDTO.getPrevalentStroke());
        healthRecord.setPrevalentHyp(createHealthRecordDTO.getPrevalentHyp());
        healthRecord.setDiabetes(createHealthRecordDTO.getDiabetes());
        healthRecord.setTotChol(createHealthRecordDTO.getTotChol());
        healthRecord.setSysBP(createHealthRecordDTO.getSysBP());
        healthRecord.setDiaBP(createHealthRecordDTO.getDiaBP());
        healthRecord.setBMI(createHealthRecordDTO.getBMI());
        healthRecord.setHeartRate(createHealthRecordDTO.getHeartRate());
        healthRecord.setGlucose(createHealthRecordDTO.getGlucose());
        healthRecord.setTenYearCHD(createHealthRecordDTO.getTenYearCHD());
        healthRecord.setSensitiveInfo(sensitiveInfoId);
        healthRecord.setCreatedDate(LocalDateTime.now());

        return healthRecordRepository.save(healthRecord);
    }

}
