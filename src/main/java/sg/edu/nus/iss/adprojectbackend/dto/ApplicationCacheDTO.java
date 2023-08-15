package sg.edu.nus.iss.adprojectbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime lastUpdated;
}
