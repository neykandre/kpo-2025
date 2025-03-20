package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.CommandMenuItem;
import java.util.Scanner;

public class CreateBankAccountMenuItem extends CommandMenuItem {
    @Override
    public String getTitle() {
        return "Create a new bank account";
    }

    @Override
    public void execute(CommandFacade facade, Scanner scanner) {
        try {
            System.out.print("Enter account name: ");
            String name = scanner.nextLine();
            BankAccount account = facade.createBankAccountCommand()
                    .setName(name)
                    .execute();
            System.out.println("Account created successfully: " + account);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while creating the account.");
        }
    }
}
