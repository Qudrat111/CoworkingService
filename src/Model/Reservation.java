package Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private String id;
    private String userId;
    private String resourceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
