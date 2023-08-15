package sg.edu.nus.iss.adprojectbackend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.model.SensitiveInfo;

@Repository
public interface SensitiveInfoRepository extends ReactiveMongoRepository<SensitiveInfo, String>{

    Mono<SensitiveInfo> findByNric(String nric);

    Mono<SensitiveInfo> findByNricAndFirstNameAndLastName(String nric, String firstName, String lastName);

    Mono<SensitiveInfo> findByNricIgnoreCase(String nric);
    
    Mono<Boolean> existsByNricIgnoreCase(String nric);
    
}
