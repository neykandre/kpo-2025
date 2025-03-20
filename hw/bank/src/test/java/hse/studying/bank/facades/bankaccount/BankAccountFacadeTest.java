package hse.studying.bank.facades.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.factories.bankaccount.BankAccountFactory;
import hse.studying.bank.providers.FinanceRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountFacadeTest {

    @Mock
    private BankAccountFactory factory;

    @Mock
    private FinanceRepository<BankAccount> repository;

    @InjectMocks
    private BankAccountFacade facade;

    @Test
    void createBankAccount_ValidInput_SavesToRepository() {
        BankAccount mockAccount = new BankAccount(1L, "Savings", 0);
        when(factory.createBankAccount("Savings")).thenReturn(mockAccount);
        when(repository.save(mockAccount)).thenReturn(mockAccount);

        BankAccount result = facade.createBankAccount("Savings");

        verify(repository).save(mockAccount);
        assertThat(result).isEqualTo(mockAccount);
    }

    @Test
    void updateBankAccount_ValidInput_UpdatesFields() {
        BankAccount existing = new BankAccount(1L, "Old", 100);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        BankAccount updated = facade.updateBankAccount(1L, "New", 200);

        assertThat(updated.getName()).isEqualTo("New");
        assertThat(updated.getBalance()).isEqualTo(200);
        verify(repository).save(existing);
    }

    @Test
    void deleteBankAccount_ExistingId_DeletesFromRepository() {
        facade.deleteBankAccount(1L);

        verify(repository).deleteById(1L);
    }
}