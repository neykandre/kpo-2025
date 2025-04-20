package hse.studying.bank.exporter;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.facades.CommandFacade;
import hse.studying.bank.interfaces.export.ExportVisitor;
import hse.studying.bank.interfaces.export.Exportable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportFacade {
    private final CommandFacade commandFacade;

    public void exportData(ExportVisitor visitor, List<?> data, Path filePath) {
        try {
            data.forEach(item -> {
                try {
                    ((Exportable) item).accept(visitor);
                } catch (java.rmi.server.ExportException e) {
                    throw new RuntimeException(e);
                }
            });
            String result = visitor.getResult();
            Files.writeString(filePath, result);
        } catch (IOException e) {
            throw new ExportException("File write error", e);
        }
    }

    public List<BankAccount> getAllAccounts() {
        return (List<BankAccount>) commandFacade.getAllBankAccountsCommand().execute();
    }

    public List<Category> getAllCategories() {
        return (List<Category>) commandFacade.getAllCategoriesCommand().execute();
    }

    public List<Operation> getAllOperations() {
        return (List<Operation>) commandFacade.getAllOperationsCommand().execute();
    }
}