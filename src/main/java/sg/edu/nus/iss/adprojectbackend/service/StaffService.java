package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.StaffDTO;
import sg.edu.nus.iss.adprojectbackend.model.Staff;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.model.Staff;

public interface StaffService {

    Flux<StaffDTO> getStaff();


    Mono<Staff> findByEmail(String email);

    Mono<Staff> createStaff(Staff newStaff);

    Mono<Staff> updateStaff(String staffId, Staff updatedStaff);

    Mono<Void> deleteStaff(String staffId);

    Mono<Staff> getStaffById(String staffId);


    public Mono<Boolean> checkEmailExist(String email);

}
