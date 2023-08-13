package sg.edu.nus.iss.adprojectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime updatedDate;
}
