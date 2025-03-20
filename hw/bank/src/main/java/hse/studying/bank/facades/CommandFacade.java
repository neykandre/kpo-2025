package hse.studying.bank.facades;

import hse.studying.bank.commands.analysis.CalculateBalanceDifferenceCommand;
import hse.studying.bank.commands.analysis.GetIncomeByCategoryCommand;
import hse.studying.bank.commands.analysis.GetOutcomeByCategoryCommand;
import hse.studying.bank.commands.bankaccount.CreateBankAccountCommand;
import hse.studying.bank.commands.bankaccount.DeleteBankAccountCommand;
import hse.studying.bank.commands.bankaccount.GetAllBankAccountsCommand;
import hse.studying.bank.commands.bankaccount.GetBankAccountCommand;
import hse.studying.bank.commands.bankaccount.UpdateBankAccountCommand;
import hse.studying.bank.commands.category.CreateCategoryCommand;
import hse.studying.bank.commands.category.DeleteCategoryCommand;
import hse.studying.bank.commands.category.GetAllCategoriesCommand;
import hse.studying.bank.commands.category.GetCategoryCommand;
import hse.studying.bank.commands.category.UpdateCategoryCommand;
import hse.studying.bank.commands.management.RecalculateBalanceCommand;
import hse.studying.bank.commands.operations.CreateOperationCommand;
import hse.studying.bank.commands.operations.DeleteOperationCommand;
import hse.studying.bank.commands.operations.GetAllOperationsCommand;
import hse.studying.bank.commands.operations.GetOperationCommand;
import hse.studying.bank.commands.operations.UpdateOperationCommand;
import hse.studying.bank.facades.analysis.AnalysisFacade;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.facades.category.CategoryFacade;
import hse.studying.bank.facades.management.ManagementFacade;
import hse.studying.bank.facades.operation.OperationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CommandFacade {

    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;
    private final BalanceFacade balanceFacade;
    private final AnalysisFacade analysisFacade;
    private final ManagementFacade managementFacade;

    public CreateBankAccountCommand createBankAccountCommand() {
        return new CreateBankAccountCommand(bankAccountFacade);
    }

    public GetBankAccountCommand getBankAccountCommand(Long id) {
        return new GetBankAccountCommand(bankAccountFacade).setId(id);
    }

    public GetAllBankAccountsCommand getAllBankAccountsCommand() {
        return new GetAllBankAccountsCommand(bankAccountFacade);
    }

    public DeleteBankAccountCommand deleteBankAccountCommand(Long id) {
        return new DeleteBankAccountCommand(bankAccountFacade).setId(id);
    }

    public UpdateBankAccountCommand updateBankAccountCommand(Long id) {
        return new UpdateBankAccountCommand(bankAccountFacade).setId(id);
    }

    public CreateCategoryCommand createCategoryCommand() {
        return new CreateCategoryCommand(categoryFacade);
    }

    public GetCategoryCommand getCategoryCommand(Long id) {
        return new GetCategoryCommand(categoryFacade).setId(id);
    }

    public GetAllCategoriesCommand getAllCategoriesCommand() {
        return new GetAllCategoriesCommand(categoryFacade);
    }

    public DeleteCategoryCommand deleteCategoryCommand(Long id) {
        return new DeleteCategoryCommand(categoryFacade).setId(id);
    }

    public UpdateCategoryCommand updateCategoryCommand(Long id) {
        return new UpdateCategoryCommand(categoryFacade).setId(id);
    }

    public CreateOperationCommand createOperationCommand() {
        return new CreateOperationCommand(operationFacade, balanceFacade);
    }

    public GetOperationCommand getOperationCommand(Long id) {
        return new GetOperationCommand(operationFacade).setId(id);
    }

    public GetAllOperationsCommand getAllOperationsCommand() {
        return new GetAllOperationsCommand(operationFacade);
    }

    public DeleteOperationCommand deleteOperationCommand(Long id) {
        return new DeleteOperationCommand(operationFacade, balanceFacade).setId(id);
    }

    public UpdateOperationCommand updateOperationCommand(Long id) {
        return new UpdateOperationCommand(operationFacade, balanceFacade).setId(id);
    }

    public CalculateBalanceDifferenceCommand calculateBalanceDifferenceCommand() {
        return new CalculateBalanceDifferenceCommand(analysisFacade);
    }

    public GetIncomeByCategoryCommand getIncomeByCategoryCommand() {
        return new GetIncomeByCategoryCommand(analysisFacade);
    }

    public GetOutcomeByCategoryCommand getOutcomeByCategoryCommand() {
        return new GetOutcomeByCategoryCommand(analysisFacade);
    }

    public RecalculateBalanceCommand recalculateBalanceCommand() {
        return new RecalculateBalanceCommand(managementFacade);
    }
}
