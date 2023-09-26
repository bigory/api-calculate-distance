package by.boldysh.api.calculatedistance.repository;

import by.boldysh.api.calculatedistance.entity.RateLimit;
import org.springframework.data.repository.CrudRepository;

public interface RateLimitRepository extends CrudRepository<RateLimit, Long> {
}
