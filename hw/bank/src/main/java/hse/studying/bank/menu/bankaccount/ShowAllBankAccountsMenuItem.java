package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.CommandMenuItem;
import java.util.Scanner;
import java.util.stream.StreamSupport;

public class ShowAllBankAccountsMenuItem extends CommandMenuItem {
    @Override
    public String getTitle() {
        return "Show all bank accounts";
    }

    @Override
    public void execute(CommandFacade facade, Scanner scanner) {
        try {
            var accounts = facade.getAllBankAccountsCommand().execute();
            if (StreamSupport.stream(accounts.spliterator(), false).findAny().isEmpty()) {
                System.out.println("No accounts found.");
                return;
            }
            accounts.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while showing all accounts.");
        }
    }
}
