package sg.edu.nus.iss.adprojectbackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class NewUsersDTO { // dto for finding the number of new users created within a date range
    private Long numUsers;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
