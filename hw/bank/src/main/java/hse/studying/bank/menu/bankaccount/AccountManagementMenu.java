package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.menu.SubMenu;

public class AccountManagementMenu extends SubMenu {
    public AccountManagementMenu() {
        super("Account Management");
        addItem(new CreateBankAccountMenuItem());
        addItem(new DeleteBankAccountMenuItem());
        addItem(new UpdateAccountMenu());
        addItem(new ShowAllBankAccountsMenuItem());
        addItem(new GetBankAccountMenuItem());
    }
}
