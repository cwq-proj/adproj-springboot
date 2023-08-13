package sg.edu.nus.iss.adprojectbackend.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ModelInfoDTO {
    private List<String> selectedFeatures;
    private String featureExtraction;
    private String targetLabel;
    private String selectedModel;
    private Double performance;
}
