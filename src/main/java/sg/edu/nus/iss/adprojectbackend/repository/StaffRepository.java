package sg.edu.nus.iss.adprojectbackend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.adprojectbackend.model.Staff;

@Repository
public interface StaffRepository extends ReactiveMongoRepository<Staff, String>{
    
}
