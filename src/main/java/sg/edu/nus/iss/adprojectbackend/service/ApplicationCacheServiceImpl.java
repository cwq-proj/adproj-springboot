package sg.edu.nus.iss.adprojectbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.ApplicationCacheDTO;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordDTO;
import sg.edu.nus.iss.adprojectbackend.model.ApplicationCache;
import sg.edu.nus.iss.adprojectbackend.repository.ApplicationCacheRepository;
import sg.edu.nus.iss.adprojectbackend.util.ApplicationCacheUtil;
import sg.edu.nus.iss.adprojectbackend.util.LabelCountStrategies;

import java.time.LocalDateTime;

@Service
public class ApplicationCacheServiceImpl implements  ApplicationCacheService{

    private final ApplicationCacheRepository applicationCacheRepository;
    private final ModelMapper modelMapper;
    private final HealthRecordService healthRecordServiceImpl;

    @Autowired
    public ApplicationCacheServiceImpl(ApplicationCacheRepository applicationCacheRepository,
                                       ModelMapper modelMapper,
                                       HealthRecordService healthRecordServiceImpl){
        this.applicationCacheRepository = applicationCacheRepository;
        this.modelMapper = modelMapper;
        this.healthRecordServiceImpl = healthRecordServiceImpl;
    }

    @Override
    @Transactional
    public Mono<ApplicationCacheDTO> updateHealthRecordCacheMale(String name) {
        LabelCountStrategies.MaleLabelCountStrategy strategy = new LabelCountStrategies.MaleLabelCountStrategy();
        return applicationCacheRepository.findByName(name)
                .map(applicationCache -> modelMapper.map(applicationCache, ApplicationCacheDTO.class))
                .flatMap(applicationCacheDTO -> {
                    Flux<HealthRecordDTO> healthRecordsDTO = healthRecordServiceImpl.getHealthRecords(); // Obtain the flux of health records

                    // Apply the health record cache age update using the util method and the flux of health records
                    return ApplicationCacheUtil.updateHealthRecordCacheBinary(Mono.just(applicationCacheDTO)
                                    , healthRecordsDTO, strategy)
                            .flatMap(updatedCacheDTO -> {
                                // Set the lastUpdate field to the current timestamp
                                updatedCacheDTO.setLastUpdated(LocalDateTime.now());
                                return applicationCacheRepository.save(modelMapper.map(updatedCacheDTO, ApplicationCache.class))
                                        .map(savedCache -> modelMapper.map(savedCache, ApplicationCacheDTO.class));
                            });
                })
                .onErrorResume(throwable -> {
                    // Handle errors and return a default value or a specific error response
                    return Mono.error(new Exception("An error occurred while updating health record cache."));
                });
    }

    @Override
    @Transactional
    public Mono<ApplicationCacheDTO> updateHealthRecordCacheAge(String name) {
        return applicationCacheRepository.findByName(name)
                .map(applicationCache -> modelMapper.map(applicationCache, ApplicationCacheDTO.class))
                .flatMap(applicationCacheDTO -> {
                    Flux<HealthRecordDTO> healthRecordsDTO = healthRecordServiceImpl.getHealthRecords(); // Obtain the flux of health records

                    // Apply the health record cache age update using the util method and the flux of health records
                    return ApplicationCacheUtil.updateHealthRecordCacheAge(Mono.just(applicationCacheDTO)
                                    , healthRecordsDTO)
                            .flatMap(updatedCacheDTO -> {
                                // Set the lastUpdate field to the current timestamp
                                updatedCacheDTO.setLastUpdated(LocalDateTime.now());
                                return applicationCacheRepository.save(modelMapper.map(updatedCacheDTO, ApplicationCache.class))
                                        .map(savedCache -> modelMapper.map(savedCache, ApplicationCacheDTO.class));
                            });
                })
                .onErrorResume(throwable -> {
                    // Handle errors and return a default value or a specific error response
                    return Mono.error(new Exception("An error occurred while updating health record cache."));
                });
    }

    @Transactional
    @Override
    public Mono<ApplicationCacheDTO> updateHealthRecordCacheCHD(String name) {
        LabelCountStrategies.CHDCountStrategy strategy = new LabelCountStrategies.CHDCountStrategy();
        return applicationCacheRepository.findByName(name)
                .map(applicationCache -> modelMapper.map(applicationCache, ApplicationCacheDTO.class))
                .flatMap(applicationCacheDTO -> {
                    Flux<HealthRecordDTO> healthRecordsDTO = healthRecordServiceImpl.getHealthRecords(); // Obtain the flux of health records

                    // Apply the health record cache age update using the util method and the flux of health records
                    return ApplicationCacheUtil.updateHealthRecordCacheBinary(Mono.just(applicationCacheDTO)
                                    , healthRecordsDTO, strategy)
                            .flatMap(updatedCacheDTO ->{
                                    // Set the lastUpdate field to the current timestamp
                                    updatedCacheDTO.setLastUpdated(LocalDateTime.now());
                                    return applicationCacheRepository.save(modelMapper.map(updatedCacheDTO, ApplicationCache.class))
                                            .map(savedCache -> modelMapper.map(savedCache, ApplicationCacheDTO.class));
                });
                })
                .onErrorResume(throwable -> {
                    // Handle errors and return a default value or a specific error response
                    return Mono.error(new Exception("An error occurred while updating health record cache."));
                });
    }

    @Override
    @Transactional
    public Mono<ApplicationCacheDTO> updateApplicationCache(String name) {
        LabelCountStrategies.CHDCountStrategy chdStrategy = new LabelCountStrategies.CHDCountStrategy();
        LabelCountStrategies.MaleLabelCountStrategy maleStrategy = new LabelCountStrategies.MaleLabelCountStrategy();

        return applicationCacheRepository.findByName(name)
                .map(applicationCache -> modelMapper.map(applicationCache, ApplicationCacheDTO.class))
                .flatMap(applicationCacheDTO -> {
                    Flux<HealthRecordDTO> healthRecordsDTO = healthRecordServiceImpl.getHealthRecords(); // Obtain the flux of health records

                    // Apply the health record cache updates using the util method and the flux of health records
                    Mono<ApplicationCacheDTO> chdUpdate = ApplicationCacheUtil.updateHealthRecordCacheBinary(
                            Mono.just(applicationCacheDTO), healthRecordsDTO, chdStrategy);

                    Mono<ApplicationCacheDTO> maleUpdate = ApplicationCacheUtil.updateHealthRecordCacheBinary(
                            Mono.just(applicationCacheDTO), healthRecordsDTO, maleStrategy);

                    // Apply the health record cache age update using the util method and the flux of health records
                    Mono<ApplicationCacheDTO> ageUpdate = ApplicationCacheUtil.updateHealthRecordCacheAge(
                            Mono.just(applicationCacheDTO), healthRecordsDTO);

                    // Combine the updates into a single Mono
                    return chdUpdate.zipWith(maleUpdate).zipWith(ageUpdate)
                            .flatMap(tuple -> {
                                ApplicationCacheDTO updatedCacheDTO = tuple.getT1().getT1();
                                // Set the lastUpdate field to the current timestamp
                                updatedCacheDTO.setLastUpdated(LocalDateTime.now());
                                return applicationCacheRepository.save(modelMapper.map(updatedCacheDTO, ApplicationCache.class))
                                        .map(savedCache -> modelMapper.map(savedCache, ApplicationCacheDTO.class));
                            });
                })
                .onErrorResume(throwable -> {
                    // Handle errors and return a default value or a specific error response
                    return Mono.error(new Exception("An error occurred while updating health record cache."));
                });
    }


    @Override
    public Mono<ApplicationCacheDTO> findByName(String name) {
        return applicationCacheRepository.findByName(name)
                .map(applicationCache -> modelMapper.map(applicationCache, ApplicationCacheDTO.class));
    }


}
