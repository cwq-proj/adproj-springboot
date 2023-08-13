package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Flux;
import sg.edu.nus.iss.adprojectbackend.dto.StaffDTO;

public interface StaffService {

    Flux<StaffDTO> getStaff();
    
}
