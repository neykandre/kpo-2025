package hse.studying.bank.interfaces.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountJpaRepository extends CrudRepository<BankAccount, Long> {

}
