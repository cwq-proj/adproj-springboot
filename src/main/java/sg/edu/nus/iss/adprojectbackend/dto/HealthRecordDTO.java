package sg.edu.nus.iss.adprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class HealthRecordDTO {
    private String id;
    private String sensitiveInfo;

    @Min(0)
    @Max(1)
    private int male;

    @Min(0)
    private int age;

    @Min(1)
    @Max(4)
    private int education;

    @Min(0)
    private int currentSmoker;

    @Min(0)
    private int cigsPerDay;

    @Min(0)
    @Max(1)
    @JsonProperty("BPMeds")
    private int BPMeds;

    @Min(0)
    @Max(1)
    private int prevalentStroke;

    @Min(0)
    @Max(1)
    private int prevalentHyp;

    @Min(0)
    @Max(1)
    private int diabetes;

    @Min(0)
    private double totChol;

    @Min(0)
    private double sysBP;

    @Min(0)
    private double diaBP;

    @Min(0)
    @Field("BMI")
    @JsonProperty("BMI")
    private double BMI;

    @Min(0)
    private int heartRate;

    @Min(0)
    private int glucose;

    @Min(0)
    @Max(1)
    private int tenYearCHD;

    @NotBlank
    @Field("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;
}
