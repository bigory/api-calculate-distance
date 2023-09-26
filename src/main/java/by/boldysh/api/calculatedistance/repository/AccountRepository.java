package by.boldysh.api.calculatedistance.repository;


import by.boldysh.api.calculatedistance.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUserName(String userName);

    boolean existsByUserName(String userName);


}
