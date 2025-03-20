package hse.studying.bank.menu.bankaccount;

import hse.studying.bank.commands.bankaccount.UpdateBankAccountCommand;
import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.menu.CommandMenuItem;
import java.util.Scanner;
import lombok.Setter;

public class UpdateAccountNameMenuItem extends CommandMenuItem {
    @Setter
    private UpdateBankAccountCommand command;

    @Override
    public String getTitle() {
        return "Change account name";
    }

    @Override
    public void execute(CommandFacade facade, Scanner scanner) {
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        command.setNewName(newName);
    }
}
