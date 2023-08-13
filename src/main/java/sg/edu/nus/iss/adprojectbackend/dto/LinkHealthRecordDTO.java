package sg.edu.nus.iss.adprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkHealthRecordDTO {
    private String nric;
    private String lastName;
    private String firstName;
    private String userId;
}
