package sg.edu.nus.iss.adprojectbackend.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import sg.edu.nus.iss.adprojectbackend.model.User;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String>{

    @Query("{ 'createdDate': { $gte: ?0, $lte: ?1 } }")
    Flux<User> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
