package hse.studying.bank.importer;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.facades.CommandFacade;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DataImporter<T> {
    private final CommandFacade facade;

    public final void importData(Path filePath) {
        try {
            String rawData = readFile(filePath);
            List<T> parsedData = parseData(rawData);
            validateData(parsedData);
            saveData(parsedData);
            System.out.println("Successfully imported " + parsedData.size() + " items");
        } catch (IOException e) {
            handleError("File read error: " + e.getMessage());
        } catch (ParseException e) {
            handleError("Data parsing failed: " + e.getMessage());
        } catch (ValidationException e) {
            handleError("Data validation failed: " + e.getMessage());
        }
    }

    protected String readFile(Path filePath) throws IOException {
        return Files.readString(filePath);
    }

    protected void validateData(List<T> data) throws ValidationException {
        if (data == null || data.isEmpty()) {
            throw new ValidationException("No valid data found");
        }
    }

    protected void saveData(List<T> data) {
        data.forEach(this::saveItemToDatabase);
    }

    protected void saveItemToDatabase(T item) {
        switch (item) {
            case BankAccount account -> {
                try {
                    facade.createBankAccountCommand()
                            .setName(account.getName())
                            .execute();
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to save account: " + e.getMessage());
                }
            }
            case Category category -> {
                try {
                    facade.createCategoryCommand()
                            .setName(category.getName())
                            .setType(category.getType())
                            .execute();
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to save category: " + e.getMessage());
                }
            }
            case Operation operation -> {
                try {
                    facade.createOperationCommand()
                            .setBankAccount(operation.getBankAccount())
                            .setCategory(operation.getCategory())
                            .setType(operation.getType())
                            .setAmount(operation.getAmount())
                            .setDescription(operation.getDescription())
                            .execute();
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to save operation: " + e.getMessage());
                }
            }
            default -> {
                throw new IllegalArgumentException("Unsupported item type: " + item.getClass().getName());
            }
        }
    }


    protected void handleError(String message) {
        System.err.println("Import error: " + message);
    }

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

    protected abstract List<T> parseData(String rawData) throws ParseException;
}