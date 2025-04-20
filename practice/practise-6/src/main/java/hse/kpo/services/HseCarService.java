package hse.kpo.services;

import hse.kpo.annotations.Sales;
import hse.kpo.domains.Customer;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.interfaces.SalesObserver;
import hse.kpo.interfaces.cars.CarProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Сервис продажи машин.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCarService {

    private final List<SalesObserver> observers = new ArrayList<SalesObserver>();

    private final CarProvider carProvider;

    private final CustomerProvider customerProvider;

    /**
     * Метод продажи машин
     */
    @Sales
    public void sellCars() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCar()))
                .forEach(customer -> {
                    var car = carProvider.takeCar(customer);
                    if (Objects.nonNull(car)) {
                        customer.setCar(car);
                        notifyObserversForSale(customer, ProductionTypes.CAR, car.getVin());
                    } else {
                        log.debug("No car in CarService");
                    }
                });
    }

    public void addObserver(SalesObserver observer) {
        observers.add(observer);
    }

    private void notifyObserversForSale(Customer customer, ProductionTypes productType, int vin) {
        observers.forEach(obs -> obs.onSale(customer, productType, vin));
    }


}