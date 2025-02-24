package hse.kpo.observers;

import hse.kpo.builders.ReportBuilder;
import hse.kpo.domains.Report;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.AddingObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportAddingObserver implements AddingObserver {

    private final ReportBuilder reportBuilder = new ReportBuilder();

    public Report buildReport() {
        return reportBuilder.build();
    }

    @Override
    public void onAdd(ProductionTypes productType, int vin) {
        reportBuilder.addOperation(String.format("Добавление: %s VIN-%d", productType, vin));
    }
}
