package sg.edu.nus.iss.adprojectbackend.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ServerLogoutSuccessHandler logoutService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            AuthConverter jwtAuthConverter,
            AuthManager jwtAuthManager
    ){
        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(jwtAuthManager);
        jwtFilter.setServerAuthenticationConverter(jwtAuthConverter);
        http
                .authorizeExchange(auth -> auth
                        .pathMatchers("/login/**").permitAll()
                        .pathMatchers("/api/staff/**").hasAnyRole("ADMIN","CLINIC")
                        /*.pathMatchers("/dashboard/**").permitAll()*/
                        .anyExchange().authenticated()
                )
                .logout()
                .logoutUrl("login/logout").logoutSuccessHandler(logoutService)
                .and()
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .cors();

        return http.build();
    }
}
