package hse.studying.bank.console;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.exporter.CsvExportVisitor;
import hse.studying.bank.exporter.ExportFacade;
import hse.studying.bank.exporter.JsonExportVisitor;
import hse.studying.bank.exporter.YamlExportVisitor;
import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.importer.CsvDataImporter;
import hse.studying.bank.importer.JsonDataImporter;
import hse.studying.bank.importer.YamlDataImporter;
import hse.studying.bank.interfaces.export.ExportVisitor;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsoleMenu {
    private final CommandFacade facade;
    private final Scanner scanner = new Scanner(System.in);
    private final Deque<Menu> menuStack = new ArrayDeque<>();
    private final JsonDataImporter<BankAccount> jsonBankAccountImporter;
    private final JsonDataImporter<Category> jsonCategoryImporter;
    private final JsonDataImporter<Operation> jsonOperationImporter;
    private final CsvDataImporter<BankAccount> csvBankAccountImporter;
    private final CsvDataImporter<Category> csvCategoryImporter;
    private final CsvDataImporter<Operation> csvOperationImporter;
    private final YamlDataImporter<BankAccount> yamlBankAccountImporter;
    private final YamlDataImporter<Category> yamlCategoryImporter;
    private final YamlDataImporter<Operation> yamlOperationImporter;
    private final ExportFacade exportFacade;
    private final CsvExportVisitor csvExportVisitor;
    private final YamlExportVisitor yamlExportVisitor;
    private final JsonExportVisitor jsonExportVisitor;

    public void run() {
        Menu mainMenu = new Menu(
                "Main Menu", Arrays.asList(
                new MenuItem("Manage Accounts", this::showAccountMenu),
                new MenuItem("Manage Categories", this::showCategoryMenu),
                new MenuItem("Manage Operations", this::showOperationMenu),
                new MenuItem("Analytics", this::showAnalyticsMenu),
                new MenuItem("Management", this::showManagementMenu),
                new MenuItem("Import Data", this::showImportMenu),
                new MenuItem("Export Data", this::showExportMenu),
                new MenuItem("Exit", () -> System.exit(0))
        ));
        menuStack.push(mainMenu);
        while (!menuStack.isEmpty()) {
            printCurrentMenu();
            processUserInput();
        }
    }

    private void printCurrentMenu() {
        Menu current = menuStack.peek();
        System.out.println("\n=== " + current.title + " ===");
        for (int i = 0; i < current.items.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, current.items.get(i).title);
        }
    }

    private void processUserInput() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            Menu current = menuStack.peek();

            if (choice > 0 && choice <= current.items.size()) {
                current.items.get(choice - 1).action.run();
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void showAccountMenu() {
        Menu menu = new Menu(
                "Account Management", Arrays.asList(
                new MenuItem("Create Account", this::createAccount),
                new MenuItem("Get Account by ID", this::getAccountById),
                new MenuItem("List Accounts", this::listAccounts),
                new MenuItem("Update Account", this::updateAccountMenu),
                new MenuItem("Delete Account", this::deleteAccount),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void createAccount() {
        System.out.print("Account name: ");
        String name = scanner.nextLine();

        BankAccount account = facade.createBankAccountCommand()
                .setName(name)
                .execute();

        System.out.println("Account created: " + account);
    }

    private void getAccountById() {
        BankAccount account = findAccount();
        System.out.println(account);
    }

    private void listAccounts() {
        facade.getAllBankAccountsCommand().execute().forEach(System.out::println);
    }

    private void updateAccountMenu() {
        BankAccount account = selectAccount();
        Menu menu = new Menu(
                "Update Account: " + account.getName(), Arrays.asList(
                new MenuItem("Update Name", () -> updateAccountName(account)),
                new MenuItem("Update Balance", () -> updateAccountBalance(account)),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private BankAccount selectAccount() {
        listAccounts();
        return findAccount();
    }

    private BankAccount findAccount() {
        System.out.print("Select account ID: ");
        return facade.getBankAccountCommand(Long.parseLong(scanner.nextLine()))
                .execute()
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    private void updateAccountName(BankAccount account) {
        System.out.print("New name: ");
        String name = scanner.nextLine();
        facade.updateBankAccountCommand(account.getId())
                .setNewName(name)
                .execute();
    }

    private void updateAccountBalance(BankAccount account) {
        System.out.print("New balance: ");
        double balance = Double.parseDouble(scanner.nextLine());
        facade.updateBankAccountCommand(account.getId())
                .setNewBalance(balance)
                .execute();
    }

    private void deleteAccount() {
        System.out.print("Select account ID for deletion: ");
        Long id = Long.parseLong(scanner.nextLine());
        facade.deleteBankAccountCommand(id).execute();
        System.out.println("Account deleted");
    }

    private void showCategoryMenu() {
        Menu menu = new Menu(
                "Category Management", Arrays.asList(
                new MenuItem("Create Category", this::createCategory),
                new MenuItem("Get Category by ID", this::getCategoryById),
                new MenuItem("List Categories", this::listCategories),
                new MenuItem("Update Category", this::updateCategoryMenu),
                new MenuItem("Delete Category", this::deleteCategory),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void createCategory() {
        System.out.print("Category name: ");
        String name = scanner.nextLine();

        System.out.print("Type (INCOME/OUTCOME): ");
        TransferType type = TransferType.valueOf(scanner.nextLine().toUpperCase());

        Category category = facade.createCategoryCommand()
                .setName(name)
                .setType(type)
                .execute();

        System.out.println("Category created: " + category);
    }

    private void getCategoryById() {
        Category category = findCategory();
        System.out.println(category);
    }

    private void listCategories() {
        facade.getAllCategoriesCommand().execute().forEach(System.out::println);
    }

    private void updateCategoryMenu() {
        Category category = selectCategory();
        Menu menu = new Menu(
                "Update Category: " + category.getName(), Arrays.asList(
                new MenuItem("Update Name", () -> updateCategoryName(category)),
                new MenuItem("Update Type", () -> updateCategoryType(category)),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private Category selectCategory() {
        listCategories();
        return findCategory();
    }

    private Category findCategory() {
        System.out.print("Select category ID: ");
        return facade.getCategoryCommand(Long.parseLong(scanner.nextLine()))
                .execute()
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    private void updateCategoryName(Category category) {
        System.out.print("New name: ");
        String name = scanner.nextLine();
        facade.updateCategoryCommand(category.getId())
                .setNewName(name)
                .execute();
    }

    private void updateCategoryType(Category category) {
        System.out.print("New type (INCOME/OUTCOME): ");
        TransferType type = TransferType.valueOf(scanner.nextLine().toUpperCase());
        facade.updateCategoryCommand(category.getId())
                .setNewType(type)
                .execute();
    }

    private void deleteCategory() {
        System.out.print("Select category ID for deletion: ");
        Long id = Long.parseLong(scanner.nextLine());
        facade.deleteCategoryCommand(id).execute();
        System.out.println("Category deleted");
    }

    private void showOperationMenu() {
        Menu menu = new Menu(
                "Operation Management", Arrays.asList(
                new MenuItem("Create Operation", this::createOperation),
                new MenuItem("Get Operation by ID", this::getOperationById),
                new MenuItem("List Operations", this::listOperations),
                new MenuItem("Update Operation", this::updateOperationMenu),
                new MenuItem("Delete Operation", this::deleteOperation),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void createOperation() {
        BankAccount account = selectAccount();
        Category category = selectCategory();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Operation operation = facade.createOperationCommand()
                .setBankAccount(account)
                .setCategory(category)
                .setType(category.getType())
                .setAmount(amount)
                .setDescription(description)
                .execute();

        System.out.println("Operation created: " + operation);
    }

    private void getOperationById() {
        Operation operation = findOperation();
        System.out.println(operation);
    }

    private void listOperations() {
        facade.getAllOperationsCommand().execute().forEach(System.out::println);
    }

    private void updateOperationMenu() {
        Operation operation = selectOperation();
        Menu menu = new Menu(
                "Update Operation", Arrays.asList(
                new MenuItem("Update Bank Account", () -> updateOperationBankAccount(operation)),
                new MenuItem("Update Category", () -> updateOperationCategory(operation)),
                new MenuItem("Update Type", () -> updateOperationType(operation)),
                new MenuItem("Update Amount", () -> updateOperationAmount(operation)),
                new MenuItem("Update Description", () -> updateOperationDescription(operation)),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private Operation selectOperation() {
        listOperations();
        return findOperation();
    }

    private Operation findOperation() {
        System.out.print("Select operation ID: ");
        return facade.getOperationCommand(Long.parseLong(scanner.nextLine()))
                .execute()
                .orElseThrow(() -> new IllegalArgumentException("Operation not found"));
    }

    private void updateOperationBankAccount(Operation operation) {
        BankAccount account = selectAccount();
        facade.updateOperationCommand(operation.getId())
                .setNewBankAccount(account)
                .execute();
    }

    private void updateOperationCategory(Operation operation) {
        Category category = selectCategory();
        facade.updateOperationCommand(operation.getId())
                .setNewCategory(category)
                .execute();
    }

    private void updateOperationType(Operation operation) {
        System.out.print("New type (INCOME/OUTCOME): ");
        TransferType type = TransferType.valueOf(scanner.nextLine().toUpperCase());
        facade.updateOperationCommand(operation.getId())
                .setNewType(type)
                .execute();
    }

    private void updateOperationAmount(Operation operation) {
        System.out.print("New amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        facade.updateOperationCommand(operation.getId())
                .setNewAmount(amount)
                .execute();
    }

    private void updateOperationDescription(Operation operation) {
        System.out.print("New description: ");
        String description = scanner.nextLine();
        facade.updateOperationCommand(operation.getId())
                .setNewDescription(description)
                .execute();
    }

    private void deleteOperation() {
        System.out.print("Select operation ID for deletion: ");
        Long id = Long.parseLong(scanner.nextLine());
        facade.deleteOperationCommand(id).execute();
        System.out.println("Operation deleted");
    }

    private void showAnalyticsMenu() {
        Menu menu = new Menu(
                "Analytics", Arrays.asList(
                new MenuItem("Calculate balance difference", this::calculateBalanceDifference),
                new MenuItem("Income by Category", this::showIncomeByCategory),
                new MenuItem("Expenses by Category", this::showExpensesByCategory),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void calculateBalanceDifference() {
        BankAccount account = selectAccount();

        Date[] period = getDatePeriod();
        double balance = facade.calculateBalanceDifferenceCommand()
                .setBankAccount(account)
                .setStartDate(period[0])
                .setEndDate(period[1])
                .execute();

        System.out.printf("Balance difference: %.2f", balance);
    }

    private void showIncomeByCategory() {
        BankAccount account = selectAccount();
        Date[] period = getDatePeriod();

        Map<Category, List<Operation>> incomeGroups = facade.getIncomeByCategoryCommand()
                .setBankAccount(account)
                .setStartDate(period[0])
                .setEndDate(period[1])
                .execute();

        printCategoryReport("Income Breakdown by Category", incomeGroups);
    }

    private void showExpensesByCategory() {
        BankAccount account = selectAccount();
        Date[] period = getDatePeriod();

        Map<Category, List<Operation>> expenseGroups = facade.getOutcomeByCategoryCommand()
                .setBankAccount(account)
                .setStartDate(period[0])
                .setEndDate(period[1])
                .execute();

        printCategoryReport("Expenses Breakdown by Category", expenseGroups);
    }

    private Date[] getDatePeriod() {
        System.out.print("Start date (yyyy-MM-dd): ");
        Date start = parseDate(scanner.nextLine());

        System.out.print("End date (yyyy-MM-dd): ");
        Date end = parseDate(scanner.nextLine());

        return new Date[] {start, end};
    }

    private void printCategoryReport(String title, Map<Category, List<Operation>> data) {
        System.out.println("\n=== " + title + " ===");

        if (data.isEmpty()) {
            System.out.println("No data found");
            return;
        }

        data.forEach((category, operations) -> {
            double total = operations.stream()
                    .mapToDouble(Operation::getAmount)
                    .sum();

            System.out.printf("\nCategory: %-20s Total: %.2f\n", category.getName(), total);
            operations.forEach(op -> System.out.printf(
                    "  - %tF : %-30s %.2f\n",
                    op.getDate(), op.getDescription(), op.getAmount()));
        });
    }

    private Date parseDate(String input) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(input);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + input, e);
        }
    }

    private void showManagementMenu() {
        Menu menu = new Menu(
                "Management", Arrays.asList(
                new MenuItem("Recalculate balance", this::recalculateBalance),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void recalculateBalance() {
        BankAccount account = selectAccount();
        double balance = facade.recalculateBalanceCommand()
                .setBankAccount(account)
                .execute();
        System.out.printf("New balance: %.2f\n", balance);
    }

    private void showImportMenu() {
        Menu menu = new Menu(
                "Data Import", Arrays.asList(
                new MenuItem("Import Bank Accounts", () -> importData(BankAccount.class)),
                new MenuItem("Import Categories", () -> importData(Category.class)),
                new MenuItem("Import Operations", () -> importData(Operation.class)),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void importData(Class<?> dataType) {
        try {
            System.out.print("Enter file path: ");
            String path = scanner.nextLine();

            System.out.print("File type (json/csv/yaml): ");
            String fileType = scanner.nextLine().toLowerCase();

            Path filePath = Path.of(path);

            switch (fileType) {
                case "json" -> {
                    if (dataType == BankAccount.class) {
                        jsonBankAccountImporter.importData(filePath);
                    } else if (dataType == Category.class) {
                        jsonCategoryImporter.importData(filePath);
                    } else {
                        jsonOperationImporter.importData(filePath);
                    }
                }
                case "csv" -> {
                    if (dataType == BankAccount.class) {
                        csvBankAccountImporter.importData(filePath);
                    } else if (dataType == Category.class) {
                        csvCategoryImporter.importData(filePath);
                    } else {
                        csvOperationImporter.importData(filePath);
                    }
                }
                case "yaml" -> {
                    if (dataType == BankAccount.class) {
                        yamlBankAccountImporter.importData(filePath);
                    } else if (dataType == Category.class) {
                        yamlCategoryImporter.importData(filePath);
                    } else {
                        yamlOperationImporter.importData(filePath);
                    }
                }
                default -> System.out.println("Unsupported file type");
            }
        } catch (Exception e) {
            System.out.println("Import failed: " + e.getMessage());
        }
    }

    private void showExportMenu() {
        Menu menu = new Menu(
                "Data Export", Arrays.asList(
                new MenuItem("Export Accounts", () -> exportData(BankAccount.class)),
                new MenuItem("Export Categories", () -> exportData(Category.class)),
                new MenuItem("Export Operations", () -> exportData(Operation.class)),
                new MenuItem("Back", menuStack::pop)
        ));
        menuStack.push(menu);
    }

    private void exportData(Class<?> dataType) {
        try {
            System.out.print("Enter file path: ");
            Path path = Path.of(scanner.nextLine());

            System.out.print("Format (json/csv/yaml): ");
            String format = scanner.nextLine().toLowerCase();

            List<?> data = switch (dataType.getSimpleName()) {
                case "BankAccount" -> exportFacade.getAllAccounts();
                case "Category" -> exportFacade.getAllCategories();
                case "Operation" -> exportFacade.getAllOperations();
                default -> throw new IllegalArgumentException("Unsupported data type");
            };

            ExportVisitor visitor = switch (format) {
                case "json" -> jsonExportVisitor;
                case "csv" -> csvExportVisitor;
                case "yaml" -> yamlExportVisitor;
                default -> throw new IllegalArgumentException("Unsupported format");
            };

            exportFacade.exportData(visitor, data, path);
            System.out.println("Exported " + data.size() + " items to " + path);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }

    private static class Menu {
        String title;
        List<MenuItem> items;

        Menu(String title, List<MenuItem> items) {
            this.title = title;
            this.items = items;
        }
    }

    private static class MenuItem {
        String title;
        Runnable action;

        MenuItem(String title, Runnable action) {
            this.title = title;
            this.action = action;
        }
    }
}