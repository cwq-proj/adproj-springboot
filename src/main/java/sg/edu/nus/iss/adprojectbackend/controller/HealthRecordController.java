package sg.edu.nus.iss.adprojectbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.*;
import sg.edu.nus.iss.adprojectbackend.service.HealthRecordService;
import sg.edu.nus.iss.adprojectbackend.service.SensitiveInfoService;
import sg.edu.nus.iss.adprojectbackend.service.UserService;

@RestController
@RequestMapping("/health-records")
public class HealthRecordController {

    private final SensitiveInfoService sensitiveInfoServiceImpl;
    private final UserService userServiceImpl;
    private final HealthRecordService healthRecordServiceImpl;

    @Autowired
    public HealthRecordController(SensitiveInfoService sensitiveInfoServiceImpl,
                                  UserService userServiceImpl,
                                  HealthRecordService healthRecordServiceImpl) {
        this.sensitiveInfoServiceImpl = sensitiveInfoServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.healthRecordServiceImpl = healthRecordServiceImpl;
    }

    @PostMapping("get-records")
    @Transactional
    public Flux<HealthRecordDTO> getHealthRecords(@RequestBody VerifyIdDTO idDTO) {
        String userId = idDTO.getId(); // get the userId
        return userServiceImpl.findById(userId)
                .flatMapMany(user -> {
                    String sensitiveInfoId = user.getSensitiveInfo(); // get the sensitive info id
                    return healthRecordServiceImpl.findHealthRecordsBySensitiveInfo(sensitiveInfoId);
                });
    }

    @PostMapping("/link")
    @Transactional
    public Flux<HealthRecordDTO> linkHealthRecordToSensitiveInfo(@RequestBody LinkHealthRecordDTO recordDTO) {
        String nric = recordDTO.getNric();
        String firstName = recordDTO.getFirstName();
        String lastName = recordDTO.getLastName();
        String userId = recordDTO.getUserId();

        return sensitiveInfoServiceImpl.findByNricAndFirstNameAndLastName(nric, firstName, lastName)
                .flatMapMany(sensitiveInfoDTO -> {
                    if (sensitiveInfoDTO != null) {
                        String sensitiveInfoId = sensitiveInfoDTO.getId();
                        return userServiceImpl.findById(userId)
                                .flatMap(updateUser -> {
                                    updateUser.setSensitiveInfo(sensitiveInfoId);
                                    return userServiceImpl.save(Mono.just(updateUser)); // Update the sensitive info field for user
                                })
                                .then(sensitiveInfoServiceImpl.findById(sensitiveInfoId))
                                .flatMap(updatedSensitiveInfo -> {
                                    updatedSensitiveInfo.setUserId(userId);
                                    return sensitiveInfoServiceImpl.save(Mono.just(updatedSensitiveInfo)); // Save the userId in the sensitive info class
                                })
                                .thenMany(healthRecordServiceImpl.findHealthRecordsBySensitiveInfo(sensitiveInfoId))
                                .doOnError(error -> {
                                    System.err.println("Error occurred during health record retrieval: " + error.getMessage());
                                });
                    } else {
                        return Flux.empty();
                    }
                })
                .doOnError(error -> {
                    System.err.println("Error occurred during sensitive info retrieval: " + error.getMessage());
                });
    }
}
