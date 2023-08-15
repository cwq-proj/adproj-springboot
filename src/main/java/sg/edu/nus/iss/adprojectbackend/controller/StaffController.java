package sg.edu.nus.iss.adprojectbackend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.StaffDTO;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;
import sg.edu.nus.iss.adprojectbackend.model.Staff;
import sg.edu.nus.iss.adprojectbackend.service.HealthRecordService;
import sg.edu.nus.iss.adprojectbackend.service.StaffService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @Autowired
    private HealthRecordService healthRecordService;

    @GetMapping
    public Flux<StaffDTO> getAllStaff() {
        return staffService.getStaff();
    }

    @PostMapping(value="register")
    public Mono<Staff> createStaff(@RequestBody Staff newStaff) {
        return staffService.createStaff(newStaff);
    }

    @GetMapping(value="{id}")
    public Mono<Staff> getStaff(@PathVariable String id) {
        return staffService.getStaffById(id);
    }

    @PutMapping(value="{id}")
    public Mono<Staff> updateStaff(@PathVariable String id, @RequestBody Staff updatedStaff) {
        
        return staffService.updateStaff(id, updatedStaff);
    }

    @DeleteMapping(value="{id}")
    public Mono<Void> deleteStaff(@PathVariable String id) {
        return staffService.deleteStaff(id);
    }

    @PostMapping(value="patient/search")
    public Flux<HealthRecord> getAllHealthRecordsByNric(@RequestHeader("nric") String nric) {
        return healthRecordService.getSortedHealthRecordsByNric(nric.toUpperCase());
    }

    @GetMapping(value="patient/search/{id}")
    public Mono<HealthRecord> getHealthRecord(@PathVariable String id) {
        return healthRecordService.getHealthRecord(id);
    }

    @DeleteMapping(value="patient/search/{id}")
    public Mono<Void> deleteHealthRecord(@PathVariable String id) {
        return healthRecordService.deleteHealthRecord(id);
    }

    @PatchMapping(value="patient/search/{id}")
    public Mono<HealthRecord> updateHealthRecord(@PathVariable String id, @RequestBody HealthRecord updatedRecord) {
        return healthRecordService.updateHealthRecord(id, updatedRecord);
    }
    
}
