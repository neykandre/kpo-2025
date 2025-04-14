package hse.kpo.services.catamarans;

import hse.kpo.domains.Customer;
import hse.kpo.domains.catamarans.Catamaran;
import hse.kpo.interfaces.CustomerProvider;
import hse.kpo.interfaces.catamarans.CatamaranFactory;
import hse.kpo.interfaces.catamarans.CatamaranProvider;
import hse.kpo.interfaces.catamarans.CatamaranRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Сервис продажи катамаранов.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HseCatamaranService implements CatamaranProvider {

    private final CustomerProvider customerProvider;

    private final CatamaranRepository catamaranRepository;

    /**
     * Метод продажи катамаранов.
     */
    public void sellCatamarans() {
        // получаем список покупателей
        var customers = customerProvider.getCustomers();
        // пробегаемся по полученному списку
        customers.stream().filter(customer -> Objects.isNull(customer.getCatamaran()))
                .forEach(customer -> {
                    var catamaran = this.takeCatamaran(customer);
                    if (Objects.nonNull(catamaran)) {
                        customer.setCatamaran(catamaran);
                    } else {
                        log.warn("No catamaran in CatamaranService");
                    }
                });
    }

    @Override
    public Catamaran takeCatamaran(Customer customer) {

        var filteredCatamarans =
                catamaranRepository.findAll().stream().filter(catamaran -> catamaran.isCompatible(customer)).toList();

        var firstCatamaran = filteredCatamarans.stream().findFirst();

        firstCatamaran.ifPresent(catamaranRepository::delete);

        return firstCatamaran.orElse(null);
    }

    /**
     * Метод добавления {@link Catamaran} в систему.
     *
     * @param catamaranFactory фабрика для создания катамаранов
     * @param catamaranParams  параметры для создания катамаранов
     */
    public <T> Catamaran addCatamaran(CatamaranFactory<T> catamaranFactory, T catamaranParams) {
        return catamaranRepository.save(catamaranFactory.create(catamaranParams));
    }

    public Catamaran addExistingCatamaran(Catamaran catamaran) {
        return catamaranRepository.save(catamaran);
    }

    public Optional<Catamaran> findByVin(Integer vin) {
        return catamaranRepository.findById(vin);
    }

    public void deleteByVin(Integer vin) {
        catamaranRepository.deleteById(vin);
    }

    public List<Catamaran> getCatamaransWithFiltration(String engineType, Integer vin) {
        return catamaranRepository.findCatamaransByEngineTypeAndVinGreaterThan(engineType, vin);
    }

    public List<Catamaran> getCatamarans() {
        return catamaranRepository.findAll();
    }
}