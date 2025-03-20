package hse.studying.bank.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.interfaces.export.ExportVisitor;
import org.springframework.stereotype.Component;

@Component
public class JsonExportVisitor implements ExportVisitor {
    private final ObjectMapper mapper = new ObjectMapper();
    private final StringBuilder result = new StringBuilder("[");

    @Override
    public void visit(BankAccount account) throws ExportException {
        try {
            if (result.length() > 1) {
                result.append(",");
            }
            result.append(mapper.writeValueAsString(account));
        } catch (Exception e) {
            throw new ExportException("JSON export error", e);
        }
    }

    @Override
    public void visit(Category category) throws ExportException {
        try {
            if (result.length() > 1) {
                result.append(",");
            }
            result.append(mapper.writeValueAsString(category));
        } catch (Exception e) {
            throw new ExportException("JSON export error", e);
        }
    }

    @Override
    public void visit(Operation operation) throws ExportException {
        try {
            if (result.length() > 1) {
                result.append(",");
            }
            result.append(mapper.writeValueAsString(operation));
        } catch (Exception e) {
            throw new ExportException("JSON export error", e);
        }
    }

    @Override
    public String getResult() {
        return result.append("]").toString();
    }
}