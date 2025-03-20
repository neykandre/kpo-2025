package hse.studying.bank.menu.category;

import hse.studying.bank.menu.SubMenu;

public class UpdateCategoryMenu extends SubMenu {
    public UpdateCategoryMenu() {
        super("Update category");
        addItem(new UpdateCategoryNameMenuItem());
        addItem(new UpdateCategoryTypeMenuItem());
    }
}
