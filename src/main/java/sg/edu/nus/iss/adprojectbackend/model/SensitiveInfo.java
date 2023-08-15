package sg.edu.nus.iss.adprojectbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Builder
@EqualsAndHashCode
@Document(collection = "sensitive_info")
public class SensitiveInfo {
    @Id
    private String id;

    @Field("nric")
    @NotBlank
    private String nric;

    @Field("firstName")
    @NotBlank
    private String firstName;

    @NotBlank
    @Field("lastName")
    private String lastName;

    @NotBlank
    @Field("phoneNumber")
    private int phoneNumber;

    @Field("userId")
    private String userId;

    @Field("healthRecord_Id")
    private String healthRecord;

    @NotBlank
    @Field("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;
}
