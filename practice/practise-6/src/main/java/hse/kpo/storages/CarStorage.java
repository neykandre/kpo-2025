package hse.kpo.storages;

import hse.kpo.domains.Car;
import hse.kpo.domains.Customer;
import hse.kpo.enums.ProductionTypes;
import hse.kpo.interfaces.AddingObserver;
import hse.kpo.interfaces.cars.CarFactory;
import hse.kpo.interfaces.cars.CarProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Хранилище информации о машинах.
 */
@Component
public class CarStorage implements CarProvider {

    private final List<AddingObserver> observers = new ArrayList<AddingObserver>();

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Car takeCar(Customer customer) {

        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();

        var firstCar = filteredCars.stream().findFirst();

        firstCar.ifPresent(cars::remove);

        return firstCar.orElse(null);
    }

    /**
     * Метод добавления {@link Car} в систему.
     *
     * @param carFactory фабрика для создания автомобилей
     * @param carParams параметры для создания автомобиля
     */
    public <ProductionParams> void addCar(CarFactory<ProductionParams> carFactory, ProductionParams carParams) {
        var car = carFactory.create(
                carParams,
                ++carNumberCounter
        );

        cars.add(car);

        notifyObserversForAdd(ProductionTypes.CAR, car.getVin());
    }

    public void addObserver(AddingObserver observer) {
        observers.add(observer);
    }

    public void notifyObserversForAdd(ProductionTypes productType, int vin) {
        observers.forEach(observer -> observer.onAdd(productType, vin));
    }
}
