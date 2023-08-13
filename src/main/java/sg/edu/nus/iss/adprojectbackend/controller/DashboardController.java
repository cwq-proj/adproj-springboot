package sg.edu.nus.iss.adprojectbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import sg.edu.nus.iss.adprojectbackend.dto.*;
import sg.edu.nus.iss.adprojectbackend.service.*;

import java.time.LocalDateTime;
import java.time.Period;

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

    @GetMapping(value = "sensitive-info", produces = MediaType.APPLICATION_JSON_VALUE)
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
                    LocalDateTime dateTo = newUsersDTO.getDateTo() != null ? newUsersDTO.getDateTo().plusMonths(1) : LocalDateTime.now();
                    LocalDateTime dateFrom = newUsersDTO.getDateFrom() != null ? newUsersDTO.getDateFrom().plusMonths(1) :
                            LocalDateTime.now().minus(Period.ofMonths(1));

                    LocalDateTime adjustedDateFrom = dateFrom.withMonth(dateFrom.getMonthValue());
                    LocalDateTime adjustedDateTo = dateTo.withMonth(dateTo.getMonthValue());

                    return userServiceImpl.findAllByCreatedDateBetween(adjustedDateFrom, adjustedDateTo)
                            .count()
                            .map(count ->
                                    NewUsersDTO.builder()
                                            .numUsers(count)
                                            .dateFrom(adjustedDateFrom)
                                            .dateTo(adjustedDateTo)
                                            .build()
                            );
                });
    }

    @GetMapping(value = "sync-data")
    public Mono<ApplicationCacheDTO> getNewUsers() {
        return applicationCacheServiceImpl.updateApplicationCache("info");
    }


    // Methods from this point on are for testing sse

    private int counter = 0;
    @GetMapping(value = "/health-records-sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<HealthRecordDTO> getHealthRecordsSSE() {
        counter ++;
        Flux<HealthRecordDTO> healthRecordsFlux = healthRecordServiceImpl.getHealthRecords();

        return healthRecordsFlux
                .doFinally(signalType -> {
                    if (signalType == SignalType.ON_COMPLETE) {
                        // Log or perform any necessary cleanup when the stream completes
                        System.out.println("SSE stream completed");
                        System.out.println(counter);
                    }
                });
    }
}
