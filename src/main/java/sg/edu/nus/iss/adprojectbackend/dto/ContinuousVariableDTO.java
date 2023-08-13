package sg.edu.nus.iss.adprojectbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ContinuousVariableDTO {
    @NotBlank
    private List<Double> xData;
    @NotBlank
    private List<Double> yCount;
}
