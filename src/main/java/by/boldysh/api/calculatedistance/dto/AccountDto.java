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
public class AccountDto implements Serializable {

    private Long id;
    private String userName;
    private String password;
    private RateLimitDto rateLimitDto;
}
