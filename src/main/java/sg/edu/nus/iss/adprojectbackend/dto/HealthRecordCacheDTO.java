package sg.edu.nus.iss.adprojectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class HealthRecordCacheDTO {
    @NotBlank
    private BinaryClassificationDTO male;
    @NotBlank
    private BinaryClassificationDTO tenYearCHD;
    @NotBlank
    private ContinuousVariableDTO age;
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedDate;
}
