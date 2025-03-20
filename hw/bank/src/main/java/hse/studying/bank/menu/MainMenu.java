package hse.studying.bank.menu;

import hse.studying.bank.menu.bankaccount.AccountManagementMenu;

public class MainMenu extends SubMenu {
    public MainMenu() {
        super("Main menu");
        addItem(new AccountManagementMenu());
    }
}
