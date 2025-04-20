package hse.studying.bank.commands.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.facades.category.CategoryFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class GetCategoryCommand implements Command<Optional<Category>> {

    private final CategoryFacade categoryFacade;
    @NotNull(message = "Category id cannot be null")
    private Long id;

    @Override
    @Transactional
    public Optional<Category> execute() {
        return categoryFacade.getCategory(id);
    }
}
