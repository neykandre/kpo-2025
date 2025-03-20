package hse.studying.bank.menu;

import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.interfaces.MenuItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubMenu implements MenuItem {
    private final String title;
    protected final List<MenuItem> items = new ArrayList<>();

    public void addItem(MenuItem item) {
        items.add(item);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void execute(CommandFacade facade, Scanner scanner) {
        while (true) {
            printMenu();
            int choice = readChoice(scanner);
            if (choice == 0) {
                return;
            }
            if (choice > 0 && choice <= items.size()) {
                items.get(choice - 1).execute(facade, scanner);
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== " + title + " ===");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getTitle());
        }
        System.out.println("0. Back");
        System.out.print("Select: ");
    }

    private int readChoice(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Enter a number: ");
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
