package hse.studying.bank.domains.operation;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.interfaces.Identifiable;
import hse.studying.bank.interfaces.export.ExportVisitor;
import hse.studying.bank.interfaces.export.Exportable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.rmi.server.ExportException;
import java.util.Date;
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
public class Operation implements Identifiable<Long>, Exportable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    private TransferType type;
    private double amount;
    private Date date;
    private String description = "";

    @Override
    public void accept(ExportVisitor visitor) throws ExportException {
        visitor.visit(this);
    }
}
