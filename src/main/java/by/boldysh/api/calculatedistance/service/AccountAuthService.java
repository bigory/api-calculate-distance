package by.boldysh.api.calculatedistance.service;


import by.boldysh.api.calculatedistance.entity.Account;
import by.boldysh.api.calculatedistance.repository.AccountRepository;
import by.boldysh.api.calculatedistance.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AccountAuthService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountAuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));
        return UserDetailServiceImpl.build(account);
    }
}
