package sg.edu.nus.iss.adprojectbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import sg.edu.nus.iss.adprojectbackend.dto.*;
import sg.edu.nus.iss.adprojectbackend.mapper.SensitiveInfoMapper;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.service.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final HealthRecordService healthRecordServiceImpl;
    private final UserService userServiceImpl;
    private final SensitiveInfoService sensitiveInfoServiceImpl;
    private final ApplicationCacheService applicationCacheServiceImpl;
    private final StaffService staffServiceImpl;

    @Autowired
    public DashboardController(HealthRecordService healthRecordServiceImpl,
                               UserService userServiceImpl,
                               SensitiveInfoService sensitiveInfoServiceImpl,
                               ApplicationCacheService applicationCacheServiceImpl,
                               StaffService staffServiceImpl) {
        this.healthRecordServiceImpl = healthRecordServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.sensitiveInfoServiceImpl = sensitiveInfoServiceImpl;
        this.applicationCacheServiceImpl = applicationCacheServiceImpl;
        this.staffServiceImpl = staffServiceImpl;
    }


    @GetMapping(value = "/health-records")
    public Flux<HealthRecordDTO> getHealthRecords() {
        return healthRecordServiceImpl.getHealthRecords();
    }

    @GetMapping(value = "users")
    public Flux<UserDTO> getUsers() {
        return userServiceImpl.getUsers();
    }

    @GetMapping(value = "staff")
    public Flux<StaffDTO> getStaff() {
        return staffServiceImpl.getStaff();
    }

    @GetMapping(value = "sensitive-info")
    public Flux<SensitiveInfoDTO> getSensitiveInfoList() {
        return sensitiveInfoServiceImpl.getSensitiveInfoList();
    }

    @GetMapping(value = "application-cache")
    public Mono<ApplicationCacheDTO> getApplicationCache() {
        return applicationCacheServiceImpl.findByName("info"); // this is to retrieve the information so it is hardcoded
    }

    @PostMapping(value = "new-users")
    public Mono<NewUsersDTO> getNewUsers(@RequestBody Mono<NewUsersDTO> newUsersDTOMono) {
        return newUsersDTOMono
                .defaultIfEmpty(new NewUsersDTO()) // Use an empty NewUsersDTO as default value
                .flatMap(newUsersDTO -> {
                    LocalDateTime dateTo = newUsersDTO.getDateTo() != null ? newUsersDTO.getDateTo()
                            : LocalDateTime.now();
                    LocalDateTime dateFrom = newUsersDTO.getDateFrom() != null ? newUsersDTO.getDateFrom()
                            : LocalDateTime.now().minus(Period.ofMonths(1));

                    LocalDateTime adjustedDateFrom = dateFrom.withMonth(dateFrom.getMonthValue());
                    LocalDateTime adjustedDateTo = dateTo.withMonth(dateTo.getMonthValue());

                    return userServiceImpl.findAllByCreatedDateBetween(adjustedDateFrom, adjustedDateTo)
                            .count()
                            .map(count -> NewUsersDTO.builder()
                                    .numUsers(count)
                                    .dateFrom(adjustedDateFrom)
                                    .dateTo(adjustedDateTo)
                                    .build());
                });
    }

    @GetMapping(value = "sync-data")
    public Mono<ApplicationCacheDTO> getNewUsers() {
        return applicationCacheServiceImpl.updateApplicationCache("info");
    }

    @GetMapping("/updateUserDetails")
    public Mono<UserDTO> userDetails() {
        // include httpsession to get email
        String email = "test@example.com";

        return userServiceImpl.findUserByEmail(email);
    }

    @PutMapping("/updateUserDetails")
    @Transactional
    public Mono<User> updateUserDetails(@RequestBody UserDTO userDTO) {
        // include httpsession to get email
        String email = "test@example.com";

        return userServiceImpl.updateUserAcc(userDTO, email);
    }

    @GetMapping(value = "/createHealthRecord/{nric}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> patientInfo(@PathVariable String nric) {
        return sensitiveInfoServiceImpl.getSensitiveInfoByNric(nric)
                .map(SensitiveInfoMapper::toFormattedMap);
    }

    @Transactional
    @PostMapping("/createHealthRecord")
    public Mono<HealthRecord> createHealthRecord(@RequestBody CreateHealthRecordDTO createHealthRecordDTO) {
        return healthRecordServiceImpl.createHealthRecord(createHealthRecordDTO);
    }

    @GetMapping("/updateHealthRecord/{id}")
    public Mono<HealthRecordDTO> viewHealthRecord(@PathVariable("id") String healthRecordId) {
        return healthRecordServiceImpl.getHealthRecordById(healthRecordId);
    }

    @PutMapping("/updateHealthRecord")
    @Transactional
    public Mono<HealthRecord> updateHealthRecord(@RequestBody HealthRecordDTO healthRecordDTO) {
        return healthRecordServiceImpl.updateHealthRecord(healthRecordDTO);
    }

    @GetMapping(value ="/updateSensitiveInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Object>> viewSensitiveInfo(@PathVariable("id") String sensitiveInfoId) {
        return sensitiveInfoServiceImpl.getSensitiveInfoById(sensitiveInfoId)
                .map(SensitiveInfoMapper::toFormattedMap);
    }

    @PutMapping("/updateSensitiveInfo")
    @Transactional
    public Mono<SensitiveInfo> updateSensitiveInfo(@RequestBody SensitiveInfoDTO sensitiveInfoDTO) {
        return sensitiveInfoServiceImpl.updateSensitiveInfo(sensitiveInfoDTO);
    }

    @GetMapping("/checkNRIC/{nric}")
    public Mono<Boolean> checkEmailExist(@PathVariable String nric) {
        return sensitiveInfoServiceImpl.checkNricExist(nric);
    }
}
