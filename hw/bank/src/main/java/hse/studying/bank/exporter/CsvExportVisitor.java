package hse.studying.bank.exporter;

import com.opencsv.CSVWriter;
import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.interfaces.export.ExportVisitor;
import java.io.StringWriter;
import org.springframework.stereotype.Component;

@Component
public class CsvExportVisitor implements ExportVisitor {
    private final StringWriter sw = new StringWriter();
    private final CSVWriter writer = new CSVWriter(sw);
    private boolean headersWritten = false;

    @Override
    public void visit(BankAccount account) {
        writeHeaders(new String[] {"id", "name", "balance"});
        writer.writeNext(new String[] {
                account.getId().toString(),
                account.getName(),
                String.valueOf(account.getBalance())
        });
    }

    @Override
    public void visit(Category category) {
        writeHeaders(new String[] {"id", "name", "type"});
        writer.writeNext(new String[] {
                category.getId().toString(),
                category.getName(),
                category.getType().name()
        });
    }

    @Override
    public void visit(Operation operation) {
        writeHeaders(new String[] {"id", "account_id", "category_id", "type", "amount", "date", "description"});
        writer.writeNext(new String[] {
                operation.getId().toString(),
                operation.getBankAccount().getId().toString(),
                operation.getCategory().getId().toString(),
                operation.getType().name(),
                String.valueOf(operation.getAmount()),
                operation.getDate().toString(),
                operation.getDescription()
        });
    }

    private void writeHeaders(String[] headers) {
        if (!headersWritten) {
            writer.writeNext(headers);
            headersWritten = true;
        }
    }

    @Override
    public String getResult() throws ExportException {
        try {
            writer.close();
            return sw.toString();
        } catch (Exception e) {
            throw new ExportException("CSV export error", e);
        }
    }
}