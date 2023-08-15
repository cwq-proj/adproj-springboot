package sg.edu.nus.iss.adprojectbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.dto.ApplicationCacheDTO;
import sg.edu.nus.iss.adprojectbackend.service.ApplicationCacheService;

@SpringBootApplication
public class AdProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdProjectBackendApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner run(ApplicationCacheService applicationCacheService) {
//		return args -> {
//			System.out.println("CommandLineRunner: Starting...");
//			// test the method
//			Mono<ApplicationCacheDTO> result = applicationCacheService.updateApplicationCache("info");
//			// Subscribe to the result and handle it
//			result.subscribe(
//					updatedCacheDTO -> {
//						// Handle the updated cache DTO
//						System.out.println("CommandLineRunner: Update successful. Updated Cache: " + updatedCacheDTO);
//					},
//					error -> {
//						// Handle the error
//						System.err.println("CommandLineRunner: Error occurred. Error: " + error.getMessage());
//					},
//					() -> {
//						System.out.println("CommandLineRunner: Completed.");
//					}
//			);
//			System.out.println("CommandLineRunner: Execution completed.");
//		};
//	}
}



