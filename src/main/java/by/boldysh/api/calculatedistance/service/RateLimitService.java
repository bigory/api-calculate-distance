package by.boldysh.api.calculatedistance.service;


import by.boldysh.api.calculatedistance.dto.AccountDto;
import by.boldysh.api.calculatedistance.dto.RateLimitDto;
import by.boldysh.api.calculatedistance.entity.RateLimit;
import by.boldysh.api.calculatedistance.repository.RateLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimitService {

    private final static String RATE_LIMIT_KEY = "user:%s";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RateLimitRepository limitRepository;
    @Autowired
    private AccountService accountService;

    public boolean isRateLimited(Long id) {
        AccountDto account = accountService.getAccountById(id);
        String key = String.format(RATE_LIMIT_KEY, account);
        RateLimitDto rateLimit = account.getRateLimitDto();
        long counter = Objects.requireNonNull(redisTemplate.opsForValue().increment(key));
        if (counter == 1) {
            redisTemplate.expire(key, rateLimit.getLimitDuration(), TimeUnit.valueOf(rateLimit.getTimeUnit()));
        }
        return counter > rateLimit.getMaxLimitRequests();
    }


    public void saveRateLimit(RateLimitDto rateLimitDto) {
        RateLimit rateLimit = RateLimit.builder()
                .limitDuration(rateLimitDto.getLimitDuration())
                .maxLimitRequests(rateLimitDto.getMaxLimitRequests())
                .timeUnit(TimeUnit.valueOf("MINUTES"))
                .build();
        limitRepository.save(rateLimit);
    }

}