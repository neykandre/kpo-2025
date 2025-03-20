package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.SubMenu;
import java.util.Scanner;

public class UpdateAccountMenu extends SubMenu {
    public UpdateAccountMenu() {
        super("Update account");
        addItem(new UpdateAccountNameMenuItem());
        addItem(new UpdateAccountBalanceMenuItem());
    }

    public void execute(CommandFacade facade, Scanner scanner) {
        try {
            System.out.print("Enter account ID: ");
            Long id = readLongInput(scanner);

            var acc = facade.getBankAccountCommand(id).execute();
            if (acc.isEmpty()) {
                System.out.println("Account not found.");
                return;
            }
            System.out.println("Account: " + acc.get());

            var command = facade.updateBankAccountCommand(id);
            items.stream().filter(UpdateAccountBalanceMenuItem.class::isInstance)
                    .map(UpdateAccountBalanceMenuItem.class::cast)
                    .forEach(item -> item.setCommand(command));
            items.stream().filter(UpdateAccountNameMenuItem.class::isInstance)
                    .map(UpdateAccountNameMenuItem.class::cast)
                    .forEach(item -> item.setCommand(command));

            super.execute(facade, scanner);

            System.out.println("Account updated successfully: " + command.execute());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while updating the account.");
        }
    }
}

