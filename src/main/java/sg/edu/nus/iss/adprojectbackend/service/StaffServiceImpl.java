package sg.edu.nus.iss.adprojectbackend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.StaffDTO;
import sg.edu.nus.iss.adprojectbackend.model.Staff;
import sg.edu.nus.iss.adprojectbackend.repository.StaffRepository;

@Service
public class StaffServiceImpl implements StaffService{

    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository,
                            ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<StaffDTO> getStaff() {
        return staffRepository.findAll()
                .map(x -> modelMapper.map(x, StaffDTO.class));
    }

    @Override
    public Mono<Staff> findByEmail(String email) {
        return staffRepository.findByEmail(email);
    }
}
