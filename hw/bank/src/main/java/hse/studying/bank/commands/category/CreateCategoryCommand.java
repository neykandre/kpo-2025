package hse.studying.bank.commands.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.category.CategoryFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotBlank;
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
public class CreateCategoryCommand implements Command<Category> {

    private final CategoryFacade categoryFacade;
    @NotNull(message = "Type is required")
    private TransferType type;
    @NotBlank(message = "Name is required")
    private String name;

    @Override
    @Transactional
    public Category execute() {
        return categoryFacade.createCategory(type, name);
    }
}
