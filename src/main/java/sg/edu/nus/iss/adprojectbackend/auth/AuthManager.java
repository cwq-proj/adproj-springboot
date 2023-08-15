package sg.edu.nus.iss.adprojectbackend.auth;

import lombok.Data;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@SuppressWarnings("ClassCanBeRecord")
@Component
@Data
public class AuthManager implements ReactiveAuthenticationManager {
    final JwtService jwtService;
    final ReactiveUserDetailsService users;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(
                authentication
        )
                .cast(BearerToken.class)
                .flatMap(auth -> {
                    String username = jwtService.extractUsername(auth.getCredentials());
                    Mono<UserDetails> foundUser = users.findByUsername(username);
                    Mono<Authentication> Auth = foundUser.flatMap(u -> {
                        if (u.getUsername() == null){
                            Mono.error(new IllegalArgumentException("User not found in auth manager"));
                        }
                        if (jwtService.isTokenValid(auth.getCredentials(), u)){
                            return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getUsername(),u.getPassword(),u.getAuthorities()));
                        }
                        Mono.error(new IllegalArgumentException("Invalid or Expired token"));
                        return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(u.getUsername(),u.getPassword(),u.getAuthorities()));

                    });
                    return Auth;
                });
    }
}
