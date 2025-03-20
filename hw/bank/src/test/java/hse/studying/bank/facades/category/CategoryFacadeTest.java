package hse.studying.bank.facades.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.factories.category.CategoryFactory;
import hse.studying.bank.providers.FinanceRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryFacadeTest {

    @Mock
    private CategoryFactory factory;

    @Mock
    private FinanceRepository<Category> repository;

    @InjectMocks
    private CategoryFacade facade;

    @Test
    void createCategory_ValidData_CreatesWithFactory() {
        Category mockCategory = new Category(1L, "Food", TransferType.OUTCOME);
        when(factory.createCategory(TransferType.OUTCOME, "Food")).thenReturn(mockCategory);
        when(repository.save(mockCategory)).thenReturn(mockCategory);

        Category result = facade.createCategory(TransferType.OUTCOME, "Food");

        verify(repository).save(mockCategory);
        assertThat(result).isEqualTo(mockCategory);
    }

    @Test
    void updateCategory_ChangeType_UpdatesCorrectly() {
        Category existing = new Category(1L, "Old", TransferType.INCOME);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Category updated = facade.updateCategory(1L, TransferType.OUTCOME, "New");

        assertThat(updated.getType()).isEqualTo(TransferType.OUTCOME);
        assertThat(updated.getName()).isEqualTo("New");
    }
}