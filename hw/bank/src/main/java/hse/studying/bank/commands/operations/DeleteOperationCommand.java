package hse.studying.bank.commands.operations;

import hse.studying.bank.facades.BalanceFacade;
import hse.studying.bank.facades.operation.OperationFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class DeleteOperationCommand implements Command<Void> {

    private final OperationFacade operationFacade;
    private final BalanceFacade balanceFacade;
    @NotNull(message = "Operation id cannot be null")
    private Long id;

    @Override
    @Transactional
    public Void execute() {
        var op = operationFacade.getOperation(id);
        op.ifPresent(balanceFacade::revertOperation);
        operationFacade.deleteOperation(id);
        return null;
    }
}
