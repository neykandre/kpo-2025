package hse.studying.bank.commands.category;

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
public class DeleteCategoryCommand implements Command<Void> {

    private final CategoryFacade categoryFacade;
    @NotNull(message = "Category id cannot be null")
    private Long id;

    @Override
    @Transactional
    public Void execute() {
        categoryFacade.deleteCategory(id);
        return null;
    }
}
