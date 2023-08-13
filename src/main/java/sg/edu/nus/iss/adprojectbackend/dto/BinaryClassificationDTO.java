package sg.edu.nus.iss.adprojectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class BinaryClassificationDTO {
    @NotBlank
    private int trueLabelCount;
    @NotBlank
    private int falseLabelCount;
}
