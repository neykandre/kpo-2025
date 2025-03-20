package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.CommandMenuItem;
import java.util.Scanner;

public class DeleteBankAccountMenuItem extends CommandMenuItem {
    @Override
    public String getTitle() {
        return "Delete a bank account";
    }

    @Override
    public void execute(CommandFacade facade, Scanner scanner) {
        try {
            System.out.print("Enter account ID: ");
            Long id = readLongInput(scanner);
            facade.deleteBankAccountCommand(id).execute();
            System.out.println("Account deleted successfully.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while deleting the account.");
        }
    }
}
