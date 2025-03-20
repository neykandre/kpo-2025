package hse.studying.bank.interfaces.export;

import hse.studying.bank.interfaces.export.ExportVisitor;
import java.rmi.server.ExportException;

public interface Exportable {
    void accept(ExportVisitor visitor) throws ExportException;
}