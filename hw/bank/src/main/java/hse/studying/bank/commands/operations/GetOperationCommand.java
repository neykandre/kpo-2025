package hse.studying.bank.commands.operations;

import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.facades.operation.OperationFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class GetOperationCommand implements Command<Optional<Operation>> {

    private final OperationFacade operationFacade;
    @NotNull(message = "Operation id cannot be null")
    private Long id;

    @Override
    @Transactional
    public Optional<Operation> execute() {
        return operationFacade.getOperation(id);
    }
}
