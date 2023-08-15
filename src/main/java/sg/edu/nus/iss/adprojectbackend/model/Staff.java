package sg.edu.nus.iss.adprojectbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "staff")
@Builder
@EqualsAndHashCode
public class Staff {
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;
}
