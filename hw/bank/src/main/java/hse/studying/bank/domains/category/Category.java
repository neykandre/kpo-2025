package hse.studying.bank.domains.category;

import hse.studying.bank.enums.TransferType;
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
public class Category implements Identifiable<Long>, Exportable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private TransferType type;

    @Override
    public void accept(ExportVisitor visitor) throws ExportException {
        visitor.visit(this);
    }
}
