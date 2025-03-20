package hse.studying.bank.interfaces;

import hse.studying.bank.facades.CommandFacade;
import java.util.Scanner;

public interface MenuItem {
    String getTitle();

    void execute(CommandFacade facade, Scanner scanner);
}
