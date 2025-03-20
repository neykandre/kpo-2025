package hse.studying.bank.commands.category;

import hse.studying.bank.domains.category.Category;
import hse.studying.bank.facades.category.CategoryFacade;
import hse.studying.bank.interfaces.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class GetAllCategoriesCommand implements Command<Iterable<Category>> {

    private final CategoryFacade categoryFacade;

    @Override
    @Transactional
    public Iterable<Category> execute() {
        return categoryFacade.getCategories();
    }
}
