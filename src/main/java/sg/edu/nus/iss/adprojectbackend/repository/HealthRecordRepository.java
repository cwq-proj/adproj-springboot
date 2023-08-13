package sg.edu.nus.iss.adprojectbackend.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import sg.edu.nus.iss.adprojectbackend.model.HealthRecord;

@Repository
public interface HealthRecordRepository extends ReactiveMongoRepository<HealthRecord, String>{

    @Query("{ $and: [ { 'sensitiveInfo': { $exists: true } }, { 'sensitiveInfo': ?0 } ] }")
    Flux<HealthRecord> findBySensitiveInfo(String sensitiveInfoId);
}
