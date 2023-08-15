package sg.edu.nus.iss.adprojectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class SensitiveInfoDTO {
    private String id;

    @NotBlank
    private String nric;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private int phoneNumber;

    private String userId;

    private String healthRecord;

    @Field("createdDate")
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;
}
