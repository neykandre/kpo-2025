package hse.studying.bank.facades.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.factories.category.CategoryFactory;
import hse.studying.bank.providers.FinanceRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class CategoryFacade {

    private final CategoryFactory categoryFactory;
    private final FinanceRepository<Category> categoryRepository;

    public Category createCategory(
            @NotNull TransferType type,
            @NotBlank String name) {
        return categoryRepository.save(categoryFactory.createCategory(type, name));
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(
            @NotNull Long id,
            @NotNull TransferType newType,
            @NotBlank String newName
    ) {
        var category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        category.get().setType(newType);
        category.get().setName(newName);
        return categoryRepository.save(category.get());
    }

    public Void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return null;
    }
}
