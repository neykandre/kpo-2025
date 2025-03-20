package hse.studying.bank.interfaces.export;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import java.rmi.server.ExportException;

public interface ExportVisitor {
    void visit(BankAccount account) throws ExportException;

    void visit(Category category) throws ExportException;

    void visit(Operation operation) throws ExportException;

    String getResult() throws ExportException;
}