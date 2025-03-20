package hse.studying.bank.factories.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.annotation.Validated;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Validated
class BankAccountFactoryTest {

    @Autowired
    private BankAccountFactory factory;

    @Test
    void createBankAccount_ValidName_ReturnsCorrectObject() {
        BankAccount account = factory.createBankAccount("Main Account");

        assertThat(account)
                .isNotNull()
                .satisfies(a -> {
                    assertThat(a.getName()).isEqualTo("Main Account");
                    assertThat(a.getBalance()).isZero();
                });
    }

    @Test
    void createBankAccount_EmptyName_ThrowsException() {
        assertThatThrownBy(() -> factory.createBankAccount(""))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createBankAccount.name: must not be blank");
    }
}