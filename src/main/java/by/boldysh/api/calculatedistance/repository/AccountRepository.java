package by.boldysh.api.calculatedistance.repository;


import by.boldysh.api.calculatedistance.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByUserName(String userName);

    @Query("SELECT a FROM Account a JOIN FETCH a.rateLimit WHERE a.id=:id")
     Optional<Account> getAccountById(@Param("id") Long id);

    boolean existsByUserName(String userName);


}
