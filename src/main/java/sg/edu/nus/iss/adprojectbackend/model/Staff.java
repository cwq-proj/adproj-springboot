package sg.edu.nus.iss.adprojectbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "staff")
@Builder
@EqualsAndHashCode
public class Staff implements UserDetails {
    @Id
    private String id;

    @Field("firstName")
    @NotBlank
    private String firstName;

    @Field("lastName")
    @NotBlank
    private String lastName;

    @Field("email")
    @NotBlank
    @Email
    private String email;

    @Field("password")
    @NotBlank
    private String password;

    @Field("username")
    @NotBlank
    private String username;

    @Field("role")
    @NotBlank
    private Roles role;

    @NotBlank
    @Field("createdDate")
    private LocalDateTime createdDate;

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
