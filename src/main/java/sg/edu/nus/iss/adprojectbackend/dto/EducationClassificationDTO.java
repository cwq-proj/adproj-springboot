package sg.edu.nus.iss.adprojectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class EducationClassificationDTO {
    @NotBlank
    private int labelOneCount;
    @NotBlank
    private int labelTwoCount;
    @NotBlank
    private int labelThreeCount;
    @NotBlank
    private int labelFourCount;
}
