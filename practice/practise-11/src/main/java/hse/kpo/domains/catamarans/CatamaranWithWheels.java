package hse.kpo.domains.catamarans;

import hse.kpo.domains.AbstractEngine;
import hse.kpo.domains.Customer;
import hse.kpo.domains.cars.Car;
import hse.kpo.enums.ProductionTypes;

public class CatamaranWithWheels extends Car {
    private final Catamaran catamaran;

    public CatamaranWithWheels(Catamaran catamaran) {
        super((AbstractEngine) catamaran.getEngine());
        this.catamaran = catamaran;
    }

    @Override
    public boolean isCompatible(Customer customer) {
        return this.catamaran.getEngine().isCompatible(customer, ProductionTypes.CATAMARAN);
    }

    @Override
    public String toString() {
        return "Адаптированный катамаран VIN-" + getVin();
    }
}