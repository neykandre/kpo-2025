package hse.studying.bank.config;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.providers.FinanceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;

@Configuration
public class RepositoryConfig {

    @Bean
    public FinanceRepository<BankAccount> bankAccountFinanceRepository(
            @Qualifier("bankAccountJpaRepository") CrudRepository<BankAccount, Long> realRepo) {
        return new FinanceRepository<>(realRepo);
    }

    @Bean
    public FinanceRepository<Category> categoryFinanceRepository(
            @Qualifier("categoryJpaRepository") CrudRepository<Category, Long> realRepo) {
        return new FinanceRepository<>(realRepo);
    }

    @Bean
    public FinanceRepository<Operation> operationFinanceRepository(
            @Qualifier("operationJpaRepository") CrudRepository<Operation, Long> realRepo) {
        return new FinanceRepository<>(realRepo);
    }
}
