package sg.edu.nus.iss.adprojectbackend.service;

import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.ApplicationCacheDTO;

public interface ApplicationCacheService {

    Mono<ApplicationCacheDTO> updateHealthRecordCacheMale(String name);

    Mono<ApplicationCacheDTO> updateHealthRecordCacheAge(String name);

    Mono<ApplicationCacheDTO> updateHealthRecordCacheCHD(String name);

    Mono<ApplicationCacheDTO> updateApplicationCache(String name);


    Mono<ApplicationCacheDTO> findByName(String name);
}
