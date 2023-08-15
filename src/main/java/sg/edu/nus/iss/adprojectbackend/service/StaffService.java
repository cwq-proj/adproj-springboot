package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.StaffDTO;
import sg.edu.nus.iss.adprojectbackend.model.Staff;
import sg.edu.nus.iss.adprojectbackend.model.User;

public interface StaffService {

    Flux<StaffDTO> getStaff();


    Mono<Staff> findByEmail(String email);
}
