package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.CommandMenuItem;
import java.util.Scanner;

public class GetBankAccountMenuItem extends CommandMenuItem {
    @Override
    public String getTitle() {
        return "Get a bank account";
    }

    @Override
    public void execute(CommandFacade facade, Scanner scanner) {
        System.out.print("Enter account ID: ");
        Long id = readLongInput(scanner);
        try {
            facade.getBankAccountCommand(id).execute().ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Account not found."));
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while getting the account.");
        }
    }
}
