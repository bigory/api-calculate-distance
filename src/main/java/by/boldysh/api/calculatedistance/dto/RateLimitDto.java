package by.boldysh.api.calculatedistance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitDto implements Serializable {

    private Long id;
    private Integer limitDuration;
    private Long maxLimitRequests;
    private String timeUnit;

}
