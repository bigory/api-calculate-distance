package by.boldysh.api.calculatedistance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = "accounts")
@Entity
@Table(name = "limits", schema = "rate_limit")
public class RateLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer limitDuration;
    private Long maxLimitRequests;
    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;
    @OneToMany(mappedBy = "rateLimit")
    private List<Account> accounts;

}
