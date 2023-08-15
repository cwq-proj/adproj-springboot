package sg.edu.nus.iss.adprojectbackend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import sg.edu.nus.iss.adprojectbackend.model.Roles;
import sg.edu.nus.iss.adprojectbackend.model.Staff;
import sg.edu.nus.iss.adprojectbackend.model.User;
import sg.edu.nus.iss.adprojectbackend.repository.StaffRepository;
import sg.edu.nus.iss.adprojectbackend.repository.UserRepository;

import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByEmail(username).block();
            Staff staff = staffRepository.findByEmail(username).block();
            Logger logger = Logger.getLogger(ApplicationConfig.class.getName());
/*            userMono.hasElement().subscribe(hasUser -> {
                if (!hasUser) {
                    userMono.flatMap(u ->{
                        logger.info("User found: " + u.getUsername());
                        return Mono.just(u);
                    });
                } else {
                    staffMono.flatMap(s ->{
                        logger.info("Staff found: " + s.getUsername());
                        return Mono.just(s);
                    });
                }
            });
            return Mono.empty();*/
            if (user == null && staff != null){
                logger.info("Staff found: " + staff.getUsername());
                return Mono.just(staff);
            } else if (user != null && staff == null) {
                logger.info("User found: " + user.getUsername());
                return Mono.just(user);
            } else {
                throw new UsernameNotFoundException("User not found");
            }


/*            return userMono.flatMap(u -> {
                logger.info("User found: " + u.getUsername());
                return Mono.just(u);
            });*/
        };
            /*Mono<Staff> staffMono = staffRepository.findByEmail(username);*/
/*            Logger logger = Logger.getLogger(ApplicationConfig.class.getName());
            return Mono.zip(userMono, staffMono)
                    .flatMap(tuple -> {
                        User user = tuple.getT1();
                        Staff staff = tuple.getT2();
                        if (user == null && staff != null) {
                            logger.info("Staff found: " + staff.getUsername());
                            return Mono.just(staff); // Staff implements UserDetails
                        } else if (user != null && staff == null) {
                            logger.info("User found: " + user.getUsername());
                            return Mono.just(user); // User implements UserDetails
                        } else {
                            return Mono.error(new UsernameNotFoundException("User not found"));
                        }
                    });*/
    }

/*    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPreAuthenticationChecks(toCheck -> {});
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }*/

/*    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
