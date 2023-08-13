package sg.edu.nus.iss.adprojectbackend.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.model.ApplicationCache;

public interface ApplicationCacheRepository extends ReactiveMongoRepository<ApplicationCache, String> {
    @Query("{ $and: [ { 'name': { $exists: true } }, { 'name': ?0 } ] }")
    Mono<ApplicationCache> findByName(String name); // check if name exists and search by name


}
