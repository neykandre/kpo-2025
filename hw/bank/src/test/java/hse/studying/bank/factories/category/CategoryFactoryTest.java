package hse.studying.bank.factories.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CategoryFactoryTest {

    @Autowired
    private CategoryFactory factory;

    @Test
    void createCategory_ValidData_ReturnsCorrectObject() {
        Category category = factory.createCategory(TransferType.INCOME, "Salary");

        assertThat(category)
                .isNotNull()
                .satisfies(c -> {
                    assertThat(c.getType()).isEqualTo(TransferType.INCOME);
                    assertThat(c.getName()).isEqualTo("Salary");
                });
    }

    @Test
    void createCategory_NullType_ThrowsException() {
        assertThatThrownBy(() -> factory.createCategory(null, "Salary"))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createCategory.type: must not be null");
    }

    @Test
    void createCategory_EmptyName_ThrowsException() {
        assertThatThrownBy(() -> factory.createCategory(TransferType.INCOME, ""))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createCategory.name: must not be blank");
    }
}