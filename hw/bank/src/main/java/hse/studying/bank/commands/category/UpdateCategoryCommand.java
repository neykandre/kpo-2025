package hse.studying.bank.commands.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.category.CategoryFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class UpdateCategoryCommand implements Command<Category> {

    private final CategoryFacade categoryFacade;
    @NotNull(message = "Category id cannot be null")
    private Long id;
    private TransferType newType;
    private String newName;

    @Override
    @Transactional
    public Category execute() {
        var category = categoryFacade.getCategory(id);
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        if (newType == null) {
            newType = category.get().getType();
        }
        if (newName == null) {
            newName = category.get().getName();
        }
        return categoryFacade.updateCategory(id, newType, newName);
    }
}
