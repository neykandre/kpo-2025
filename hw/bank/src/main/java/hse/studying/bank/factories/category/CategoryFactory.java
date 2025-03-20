package hse.studying.bank.factories.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CategoryFactory {

    public Category createCategory(
            @NotNull TransferType type,
            @NotBlank String name) {
        return new Category(null, name, type);
    }
}
