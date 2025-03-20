package hse.studying.bank.commands.analysis;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.analysis.AnalysisFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class CalculateBalanceDifferenceCommand implements Command<Double> {
    private final AnalysisFacade analysisFacade;
    @NotNull(message = "Bank account cannot be null")
    private BankAccount bankAccount;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;

    @Override
    public Double execute() {
        return analysisFacade.calculateBalanceDifference(bankAccount, startDate, endDate);
    }
}
