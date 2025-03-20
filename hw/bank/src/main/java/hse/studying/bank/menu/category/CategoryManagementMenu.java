package hse.studying.bank.menu.category;

import hse.studying.bank.menu.SubMenu;

public class CategoryManagementMenu extends SubMenu {
    public CategoryManagementMenu() {
        super("Category Management");
        addItem(new CreateCategoryMenuItem());
        addItem(new DeleteCategoryMenuItem());
        addItem(new UpdateCategoryMenu());
        addItem(new ShowAllCategoriesMenuItem());
        addItem(new GetCategoryMenuItem());
    }
}
