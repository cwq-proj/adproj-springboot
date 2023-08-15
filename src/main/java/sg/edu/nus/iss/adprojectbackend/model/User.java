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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Field("firstName")
    private String firstName;

    @Field("lastName")
    @NotBlank
    private String lastName;

    @Field("email")
    @Email
    private String email;

    @Field("password")
    @NotBlank
    private String password;

    @Field("userName")
    @NotBlank
    private String username;

    @Field("sensitiveInfo_Id")
    private String sensitiveInfo;

    @NotBlank
    @Field("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;
}
