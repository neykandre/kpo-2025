package hse.studying.bank.commands.operations;

import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.facades.operation.OperationFacade;
import hse.studying.bank.interfaces.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class GetAllOperationsCommand implements Command<Iterable<Operation>> {

    private final OperationFacade operationFacade;

    @Override
    @Transactional
    public Iterable<Operation> execute() {
        return operationFacade.getOperations();
    }
}
