package sg.edu.nus.iss.adprojectbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ApplicationCacheDTO {
    private String id;
    private String name;
    private ModelInfoDTO modelInfo;
    private HealthRecordCacheDTO healthRecordCacheDTO;
    private LocalDateTime lastUpdated;
}
