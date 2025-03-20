package hse.studying.bank.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.interfaces.export.ExportVisitor;
import org.springframework.stereotype.Component;

@Component
public class YamlExportVisitor implements ExportVisitor {
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final StringBuilder result = new StringBuilder();

    @Override
    public void visit(BankAccount account) throws ExportException {
        try {
            result.append(mapper.writeValueAsString(account));
        } catch (Exception e) {
            throw new ExportException("YAML export error", e);
        }
    }

    @Override
    public void visit(Category category) throws ExportException {
        try {
            result.append(mapper.writeValueAsString(category));
        } catch (Exception e) {
            throw new ExportException("YAML export error", e);
        }
    }

    @Override
    public void visit(Operation operation) throws ExportException {
        try {
            result.append(mapper.writeValueAsString(operation));
        } catch (Exception e) {
            throw new ExportException("YAML export error", e);
        }
    }

    @Override
    public String getResult() {
        return result.toString();
    }
}