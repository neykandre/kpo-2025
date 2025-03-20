package hse.studying.bank.domains.bankaccount;

import hse.studying.bank.interfaces.Identifiable;
import hse.studying.bank.interfaces.export.ExportVisitor;
import hse.studying.bank.interfaces.export.Exportable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.rmi.server.ExportException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class BankAccount implements Identifiable<Long>, Exportable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double balance;

    @Override
    public void accept(ExportVisitor visitor) throws ExportException {
        visitor.visit(this);
    }
}
