package sg.edu.nus.iss.adprojectbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "health_record")
public class HealthRecord {
    @Id
    private String id;

    @Field("sensitiveInfo_Id")
    private String sensitiveInfo;

    @Min(0)
    @Max(1)
    @Field("male")
    private int male;

    @Min(0)
    @Field("age")
    private int age;

    @Min(1)
    @Max(4)
    @Field("education")
    private int education;

    @Min(0)
    @Max(1)
    @Field("currentSmoker")
    private int currentSmoker;

    @Min(0)
    @Field("cigsPerDay")
    private int cigsPerDay;

    @Min(0)
    @Max(1)
    @Field("BPMeds")
    @JsonProperty("BPMeds")
    private int BPMeds;

    @Min(0)
    @Max(1)
    @Field("prevalentStroke")
    private int prevalentStroke;

    @Min(0)
    @Max(1)
    @Field("prevalentHyp")
    private int prevalentHyp;

    @Min(0)
    @Max(1)
    @Field("diabetes")
    private int diabetes;

    @Min(0)
    @Field("totChol")
    private double totChol;

    @Min(0)
    @Field("sysBP")
    private double sysBP;

    @Min(0)
    @Field("diaBP")
    private double diaBP;

    @Min(0)
    @Field("BMI")
    @JsonProperty("BMI")
    private double BMI;

    @Min(0)
    @Field("heartRate")
    private int heartRate;

    @Min(0)
    @Field("glucose")
    private int glucose;

    @Min(0)
    @Max(1)
    @Field("TenYearCHD")
    private int tenYearCHD;

    @NotBlank
    @Field("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdDate;
}
