package sg.edu.nus.iss.adprojectbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;
import sg.edu.nus.iss.adprojectbackend.repository.HealthRecordRepository;

@Service
public class HealthRecordServiceImpl implements HealthRecordService{

    private final HealthRecordRepository healthRecordRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HealthRecordServiceImpl(HealthRecordRepository healthRecordRepository,
                                   ModelMapper modelMapper) {
        this.healthRecordRepository = healthRecordRepository;
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

}
