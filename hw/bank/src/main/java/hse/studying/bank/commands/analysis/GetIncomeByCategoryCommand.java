package hse.studying.bank.commands.analysis;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.facades.analysis.AnalysisFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class GetIncomeByCategoryCommand implements Command<Map<Category, List<Operation>>> {
    private final AnalysisFacade analysisFacade;
    @NotNull(message = "Bank account cannot be null")
    private BankAccount bankAccount;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;

    @Override
    public Map<Category, List<Operation>> execute() {
        return analysisFacade.getIncomeByCategory(bankAccount, startDate, endDate);
    }
}
