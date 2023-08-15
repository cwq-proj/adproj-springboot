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
    public Mono<Staff> createStaff(Staff newStaff) {
        return staffRepository.save(newStaff);
    }

    @Override
    public Mono<Staff> updateStaff(String staffId, Staff updatedStaff) {
        Mono<Staff> existingStaffMono = staffRepository.findById(staffId);

        return existingStaffMono.flatMap(existingStaff -> {
            existingStaff.setFirstName(updatedStaff.getFirstName());
            existingStaff.setLastName(updatedStaff.getLastName());
            existingStaff.setEmail(updatedStaff.getEmail());
            existingStaff.setUsername(updatedStaff.getUsername());
            existingStaff.setRole(updatedStaff.getRole());

            String newPassword = updatedStaff.getPassword();
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                existingStaff.setPassword(newPassword);
            }

            return staffRepository.save(existingStaff);
        });
    }

    @Override
    public Mono<Void> deleteStaff(String staffId) {
        return staffRepository.deleteById(staffId);
    }

    @Override
    public Mono<Staff> getStaffById(String staffId) {
        return staffRepository.findById(staffId);
    }
}
