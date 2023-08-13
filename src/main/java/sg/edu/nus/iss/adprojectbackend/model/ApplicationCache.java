package sg.edu.nus.iss.adprojectbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sg.edu.nus.iss.adprojectbackend.dto.HealthRecordCacheDTO;
import sg.edu.nus.iss.adprojectbackend.dto.ModelInfoDTO;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Document(collection="application_cache")
public class ApplicationCache {
    @Id
    private String id;

    @Field("name")
    @Indexed(unique = true)
    private String name;

    @Field("modelInfo")
    private ModelInfoDTO modelInfo;

    @Field("healthRecordCache")
    private HealthRecordCacheDTO healthRecordCacheDTO;

    @Field("lastUpdated")
    private LocalDateTime lastUpdated;
}
