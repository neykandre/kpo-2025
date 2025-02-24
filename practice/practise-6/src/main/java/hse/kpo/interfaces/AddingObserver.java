package hse.kpo.interfaces;

import hse.kpo.enums.ProductionTypes;

public interface AddingObserver {
    void onAdd(ProductionTypes productType, int vin);
}
