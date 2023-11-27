package by.boldysh.api.calculatedistance.service;


import by.boldysh.api.calculatedistance.dto.AccountDto;
import by.boldysh.api.calculatedistance.dto.RateLimitDto;
import by.boldysh.api.calculatedistance.entity.Account;
import by.boldysh.api.calculatedistance.entity.RateLimit;
import by.boldysh.api.calculatedistance.exception.AccountNotFoundException;
import by.boldysh.api.calculatedistance.repository.AccountRepository;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(cacheNames = "user", key = "#id")
    public AccountDto getAccountById(@Param("idaccount") Long id) {
        Account account = accountRepository.getAccountById(id).orElseThrow(() ->
                new AccountNotFoundException(String.format("Account %s not found", id)));
        return AccountDto.builder()
                .id(account.getId())
                .userName(account.getUserName())
                .rateLimitDto(RateLimitDto.builder()
                        .id(account.getRateLimit().getId())
                        .limitDuration(account.getRateLimit().getLimitDuration())
                        .maxLimitRequests(account.getRateLimit().getMaxLimitRequests())
                        .timeUnit(account.getRateLimit().getTimeUnit().name())
                        .build())
                .build();
    }

    @CacheEvict(cacheNames = "user", key = "#accountDto.id")
    public AccountDto deleteUser(AccountDto accountDto) {
        Account account = Account.builder()
                .id(accountDto.getId())
                .userName(accountDto.getUserName())
                .build();
        accountRepository.delete(account);
        return accountDto;
    }

    @CachePut(cacheNames = "user", key = "#accountDto.id")
    public AccountDto updateUser(AccountDto accountDto) {
        Account account = Account.builder()
                .id(accountDto.getId())
                .userName(accountDto.getUserName())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .rateLimit(RateLimit.builder()
                        .id(accountDto.getRateLimitDto().getId())
                        .limitDuration(accountDto.getRateLimitDto().getLimitDuration())
                        .maxLimitRequests(accountDto.getRateLimitDto().getMaxLimitRequests())
                        .timeUnit(TimeUnit.valueOf(accountDto.getRateLimitDto().getTimeUnit()))
                        .build())
                .build();
        accountRepository.save(account);
        return accountDto;
    }

    public boolean existByLoginUser(String userName) {
        return accountRepository.existsByUserName(userName);
    }

    public AccountDto saveUser(AccountDto accountDto) {
        Account account = Account.builder()
                .userName(accountDto.getUserName())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .rateLimit(RateLimit.builder()
                        .id(accountDto.getRateLimitDto().getId())
                        .limitDuration(accountDto.getRateLimitDto().getLimitDuration())
                        .maxLimitRequests(accountDto.getRateLimitDto().getMaxLimitRequests())
                        .timeUnit(TimeUnit.valueOf(accountDto.getRateLimitDto().getTimeUnit()))
                        .build()).build();
        Account savedAccount = accountRepository.save(account);
        accountDto.setId(savedAccount.getId());
        return accountDto;
    }
}
