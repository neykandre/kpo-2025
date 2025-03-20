package hse.studying.bank.menu;

import hse.studying.bank.interfaces.MenuItem;
import java.util.Scanner;

public abstract class CommandMenuItem implements MenuItem {

    protected Double readDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    protected Long readLongInput(Scanner scanner) {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
